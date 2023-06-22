#lang racket
;(require racket/trace)




(define (get-state-orientation state) (get-state-element state 2))
(define (get-state-coordinations state) (get-state-element state 1))
(define (get-state-coordination-x state) (get-state-element(get-state-element state 1) 0))
(define (get-state-coordination-y state) (get-state-element(get-state-element state 1) 1))
(define (make-a-list a) (cond
                          ((list? a) a)
                          (#t (list a))))

(define (get-state-maze state) (get-state-element state 0))

(define (get-state-element li n)
  (cond
    ((= n 0) (car li))
    (#t (get-state-element (cdr li) (- n 1)))
   )
 )

; orientations 
(define (north? state) (eqv? `north (get-state-orientation state)))
(define (south? state) (eqv? `south (get-state-orientation state)))
(define (west? state) (eqv? `west (get-state-orientation state)))
(define (east? state) (eqv? `east (get-state-orientation state)))

; wall / mark
(define (wall? state) (eqv? (next-field state) 'w))
(define (mark? state) (> (current-field state) 0))


(define (current-field state)
  (get-xy (get-state-maze state) (get-state-coordination-x state) (get-state-coordination-y state)))

; Put / Get mark
(define (put-mark state) (list (set-xy (get-state-maze state) (get-state-coordination-x state) (get-state-coordination-y state) (+ (get-xy (get-state-maze state) (get-state-coordination-x state) (get-state-coordination-y state)) 1))
                          (get-state-coordinations state) (get-state-orientation state)))
(define (get-mark state) (list (set-xy (get-state-maze state) (get-state-coordination-x state) (get-state-coordination-y state) (- (get-xy (get-state-maze state) (get-state-coordination-x state) (get-state-coordination-y state)) 1))
                          (get-state-coordinations state) (get-state-orientation state)))

;(define (step state))
(define (next-field state)
  (cond
    ((north? state) (get-xy (get-state-maze state) (get-state-coordination-x state) (- (get-state-coordination-y state) 1)))
    ((south? state) (get-xy (get-state-maze state) (get-state-coordination-x state) (+ (get-state-coordination-y state) 1)))
    ((east? state) (get-xy (get-state-maze state) (+ (get-state-coordination-x state) 1) (get-state-coordination-y state)))
    ((west? state) (get-xy (get-state-maze state) (- (get-state-coordination-x state) 1) (get-state-coordination-y state)))))

(define (set-coordinations state n orient)
  (cond
    ((= n 0)
       (cond
         ((eqv? orient 'north) (cons(cons(car(car state)) (list(- (car(cdr(car state))) 1))) (cdr state)))
         ((eqv? orient 'south) (cons(cons(car(car state)) (list(+ (car(cdr(car state))) 1))) (cdr state)))
         ((eqv? orient 'east) (cons(cons(+ (car(car state)) 1) (cdr(car state))) (cdr state)))
         ((eqv? orient 'west) (cons(cons(- (car(car state)) 1) (cdr(car state))) (cdr state)))
       ))
    (#t (cons(car state) (set-coordinations (cdr state) (- n 1) orient)))))

(define (step state)
  (cond
    ((eqv? (wall? state) #f) (set-coordinations state 1 (get-state-orientation state))))) 


; Turn-left
(define (set-orientation state n orient)
  (cond
    ((= 0 n) (cons orient (cdr state)))
    (#t (cons(car state) (set-orientation (cdr state) (- n 1) orient)))))

(define (turn-left state)
  (cond
    ((eqv? (get-state-orientation state) 'south) (set-orientation state 2 'east))
    ((eqv? (get-state-orientation state) 'east) (set-orientation state 2 'north))
    ((eqv? (get-state-orientation state) 'north) (set-orientation state 2 'west))
    ((eqv? (get-state-orientation state) 'west) (set-orientation state 2 'south))))

;Setting / Getting X Y
(define (set-x maze x value)
  (cond ((= x 0) (cons value (cdr maze)))
        (#t (cons(car maze) (set-x (cdr maze) (- x 1) value)))))

(define (set-xy maze x y value)
  (cond ((= y 0) (cons(set-x (car maze) x value) (cdr maze)))
        (#t (cons(car maze) (set-xy (cdr maze) x (- y 1) value)))))

(define (get-x maze x)
  (cond ((= x 0) (car maze))
        (#t (get-x (cdr maze) (- x 1)))))

(define (get-xy maze x y)
  (cond ((= y 0) (get-x (car maze) x))
        (#t (get-xy (cdr maze) x (- y 1)))))



;(trace set-orientation)    
;(trace set-x)
;(trace set-xy)



(define (simulate state expr program limit)
    (my-simulate state '() expr program limit))

(define (my-simulate state history expr program limit)
   (cond
   ((null? expr) (list history state))
   (#t (cond
            ((eqv? (car (make-a-list expr)) 'turn-left) (my-simulate (turn-left state) (append (make-a-list history) (make-a-list(car(make-a-list expr)))) (cdr (make-a-list expr)) program limit))
            ((eqv? (car (make-a-list expr)) 'put-mark) (my-simulate (put-mark state) (append (make-a-list history) (make-a-list(car(make-a-list expr)))) (cdr (make-a-list expr)) program limit))
            ((eqv? (car (make-a-list expr)) 'get-mark) (if(mark? state) (my-simulate (get-mark state) (append  (make-a-list history) (make-a-list(car(make-a-list expr)))) (cdr (make-a-list expr)) program limit) (list history state)))
                                    
            ((eqv? (car (make-a-list expr)) 'step)
                                   (cond
                                     ((wall? state) (list history state))
                                     (#t (my-simulate (step state) (append  history (car(make-a-list expr))) (cdr (make-a-list expr)) program limit))))
            (#t (run-procedure (append (make-a-list(parse-procedure program (car(make-a-list expr)))) (list 'procedure-end)) state history (cdr (make-a-list expr)) program (- limit 1)))

            ))))

(define (parse-procedure program name)
  (cond
    ((eqv? name (cadar program)) (caddar program))
    (#t (parse-procedure (cdr program) name))))
  

(define (run-procedure parsed-steps state history expr program limit)
  (cond
    ((> 0 limit) (list history state))
    ((null? parsed-steps) (my-simulate state history expr program limit))
    (#t (cond
          ((list? (car parsed-steps))
           (cond
             ((eqv? (caar parsed-steps) 'if)
              (cond
                ((eqv? (cadar parsed-steps) 'north?)
                  (if (north? state)
                      (run-procedure (append (make-a-list(caddar parsed-steps)) (make-a-list(cdr parsed-steps))) state history expr program limit)
                      (run-procedure (append (make-a-list(cadddr (car parsed-steps))) (make-a-list(cdr parsed-steps))) state history expr program limit)))
                ((eqv? (cadar parsed-steps) 'wall?)
                  (if (wall? state)
                      (run-procedure (append (make-a-list(caddar parsed-steps)) (make-a-list(cdr parsed-steps))) state history expr program limit)
                      (run-procedure (append (make-a-list(cadddr (car parsed-steps))) (make-a-list(cdr parsed-steps))) state history expr program limit)))
                ((eqv? (cadar parsed-steps) 'mark?)
                  (if (mark? state)
                      (run-procedure (append (make-a-list(caddar parsed-steps)) (make-a-list(cdr parsed-steps))) state history expr program limit)
                      (run-procedure (append (make-a-list(cadddr (car parsed-steps))) (make-a-list(cdr parsed-steps))) state history expr program limit)))))))
             ((eqv? (car parsed-steps) 'if)
               (cond
                ((eqv? (cadr parsed-steps) 'north?)
                  (if (north? state)
                      (run-procedure (append (make-a-list(caddr parsed-steps)) (make-a-list(cddddr parsed-steps))) state history expr program limit)
                      (run-procedure (append (make-a-list(cadddr parsed-steps)) (make-a-list(cddddr parsed-steps))) state history expr program limit)))
                ((eqv? (cadr parsed-steps) 'wall?)
                  (if (wall? state)
                      (run-procedure (append (make-a-list(caddr parsed-steps)) (make-a-list(cddddr parsed-steps))) state history expr program limit)
                      (run-procedure (append (make-a-list(cadddr parsed-steps)) (make-a-list(cddddr parsed-steps))) state history expr program limit)))
                ((eqv? (cadr parsed-steps) 'mark?)
                  (if (mark? state)
                      (run-procedure (append (make-a-list(caddr parsed-steps)) (make-a-list(cddddr parsed-steps))) state history expr program limit)
                      (run-procedure (append (make-a-list(cadddr parsed-steps)) (make-a-list(cddddr parsed-steps))) state history expr program limit))))
               )
             ((eqv? (car parsed-steps) 'procedure-end) (run-procedure (cdr parsed-steps) state history expr program (+ limit 1)))
             ((eqv? (car parsed-steps) 'step) (if (wall? state) (list history state) (run-procedure (cdr parsed-steps) (step state) (append (make-a-list history) (make-a-list(car parsed-steps))) expr program limit)))
             ((eqv? (car parsed-steps) 'get-mark) (if (mark? state) (run-procedure (cdr parsed-steps) (get-mark state)(append (make-a-list history) (make-a-list(car parsed-steps))) expr program limit) (list history state)))
             ((eqv? (car parsed-steps) 'put-mark) (run-procedure (cdr parsed-steps) (put-mark state) (append (make-a-list history) (make-a-list(car parsed-steps))) expr program limit))
             ((eqv? (car parsed-steps) 'turn-left) (run-procedure (cdr parsed-steps) (turn-left state) (append (make-a-list history) (make-a-list(car parsed-steps))) expr program limit))
             (#t (run-procedure (append(append (parse-procedure program (car parsed-steps)) (list `procedure-end)) (cdr parsed-steps)) state history expr program (- limit 1)))))))


;(trace my-simulate)
;(trace run-procedure)
;(trace parse-procedure)

;(trace mark?)
;(trace wall?)
;(trace get-mark)
;(trace put-mark)
;(trace north?)
;(trace step)
;(trace turn-left)

          


