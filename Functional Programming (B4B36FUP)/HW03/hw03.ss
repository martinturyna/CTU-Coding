#lang racket
(require racket/trace)
   
(define (get-state-orientation state) (get-state-element state 2))
(define (get-state-coordinations state) (get-state-element state 1))
(define (get-state-coordination-x state) (get-state-element(get-state-element state 1) 0))
(define (get-state-coordination-y state) (get-state-element(get-state-element state 1) 1))
(define (make-a-list a)
  (cond
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
   
; Manhattan distance 3 functions
(define (manhattan-for-nums my-num expected-num)
  (cond
    ((number? my-num)
     (cond
       ((= my-num expected-num) 0)
       ((> my-num expected-num) (- my-num expected-num))
       (#t (- expected-num my-num))
       ))
    (#t 0)))
   
(define (manhattan-for-rows my-row expected-row distance)
  (cond
    ((= 0 (length my-row)) distance)
    (#t (manhattan-for-rows (cdr my-row) (cdr expected-row) (+ distance (manhattan-for-nums (car my-row) (car expected-row)))))
  ))
   
(define (manhattan-distance my-maze expected-maze distance)
  (cond
    ((= 0 (length my-maze)) distance)
    (#t (manhattan-distance (cdr my-maze) (cdr expected-maze) (+ distance (manhattan-for-rows (car my-maze) (car expected-maze) 0))))
  ))
   
; Robot configuration distance, 1 function using manhattan-for-nums
(define (robot-configuration-distance my-position my-orientation expected-position expected-orientation)
  (cond
    ((eqv? my-orientation expected-orientation)
     (+ (manhattan-for-nums (car my-position) (car expected-position))
        (manhattan-for-nums (cadr my-position) (cadr expected-position))))
    (#t
     (+ 1 (manhattan-for-nums (car my-position) (car expected-position))
        (manhattan-for-nums (cadr my-position) (cadr expected-position)))
     )))
   
;The length of the program, single function
(define (length-of-the-program program)
  (cond
    ((null? program) 0)
    ((list? (car program)) (+ (length-of-the-program (car program)) (length-of-the-program (cdr program))))
    ((eqv? (car program) 'if) (+ 1 (length-of-the-program (cddr program))))
    ((eqv? (car program) 'procedure) (+ 1 (length-of-the-program (cddr program))))
    (#t (+ 1 (length-of-the-program (cdr program))))))
   
(define (evaluate programs pairs threshold stack-size)
  (evaluate-programs '() programs pairs threshold stack-size))
   
(define (evaluate-programs result programs pairs threshold stack-size)
  (cond
    ((null? programs)  (merge-sort result))
    (#t
     (let ((finished-values (evaluate-pairs (car programs) pairs threshold (cons 0 (cons 0 (cons (length-of-the-program (car programs)) (cons 0 '())))) stack-size)))
      (cond
        ((threshold? threshold finished-values) (evaluate-programs (append result (list(list finished-values (car programs)))) (cdr programs) pairs threshold stack-size))
        (#t (evaluate-programs result (cdr programs) pairs threshold stack-size)))))))
   
   
(define (evaluate-pairs program pairs threshold current-threshold stack-size)
  (cond
    ((null? pairs) current-threshold)
    (#t
      (let ((simulation-res (simulate (caar pairs) 'start program stack-size)))
        (evaluate-pairs program (cdr pairs) threshold
           (sum-thresholds (evaluate-single-pair  simulation-res (cadar pairs) program current-threshold) current-threshold)
            stack-size))
)))
   
(define (evaluate-single-pair finished expected program current-threshold)
  (list
      (manhattan-distance (caadr finished) (car expected) 0)
      (robot-configuration-distance (cadadr finished) (cadr(cdadr finished)) (cadr expected) (caddr expected))
      (caddr current-threshold)
      (length (car finished))
    ))
   
                      
   
(define (sum-thresholds threshold1 threshold2)
  (list (+ (car threshold1)(car threshold2)) (+ (cadr threshold1)(cadr threshold2)) (caddr threshold1) (+ (cadddr threshold1) (cadddr threshold2))))
   
(define (threshold? threshold current-threshold)
  (cond
    ((= (car current-threshold) -1) #f)
    ((< (car threshold) (car current-threshold)) #f)
    ((< (cadr threshold) (cadr current-threshold)) #f)
    ((< (caddr threshold) (caddr current-threshold)) #f)
    ((< (cadddr threshold) (cadddr current-threshold)) #f)
    (#t #t)))
   
   
(define (split ls)
  (cond
    ((null? ls) (cons '() '()))
    ((null? (cdr ls)) (cons ls '()))
    (#t (let ((p (split (cddr ls))))
          (cons (cons (car ls) (car p))
                (cons (cadr ls) (cdr p))))
        )
    )
 )
   
(define (merge as bs)
  (cond
    ((null? as) bs)
    ((null? bs) as)
    ((left-is-lower-or-eqv as bs)
     (cons (car as) (merge (cdr as) bs)))
     (#t (merge bs as))
 )
)
   
(define (merge-sort ls)
 (cond
   ((null? ls) ls)
   ((null? (cdr ls)) ls)
   (#t (let* ((p (split ls))
              (sas (merge-sort (car p)))
              (sbs (merge-sort (cdr p)))
              )
 (merge sas sbs)
))))
   
(define (left-is-lower-or-eqv as bs)
  (cond
    ((< (caaar as) (caaar bs)) #t)
    ((= (caaar as) (caaar bs))
     (cond
       ((< (cadaar as) (cadaar bs)) #t)
       ((= (cadaar as) (cadaar bs))
           (cond
             ((< (caddr(caar as)) (caddr(caar bs))) #t)
             ((= (caddr(caar as)) (caddr(caar bs)))
                 (cond
                   ((<= (cadddr(caar as)) (cadddr(caar bs))) #t)
                   (#t #f))
             )
              (#t #f))
        )   
       (#t #f))
     )
   (#t #f)))
  
  
(define p1 '((procedure start put-mark)))
(define p2 '((procedure start (if wall? put-mark step))))
(define p3 '((procedure start (if wall? put-mark (step start)))))
(define p4 '((procedure start (if wall? put-mark (step start turn-left turn-left step turn-left turn-left)))))
(define p5 '((procedure start (if wall? put-mark step))))
(define p6 '((procedure start (if wall? put-mark (step start)))))
(define p7 '((procedure start (if wall? put-mark (step start turn-left turn-left step turn-left turn-left)))))
(define p8 '((procedure start (put-mark (if wall? turn-left step) start))))
(define p9 '((procedure start (if wall? (turn-left start turn-left turn-left turn-left) go-and-return))
             (procedure go-and-return  (if wall? put-mark (step go-and-return turn-left turn-left step turn-left turn-left)))))
(define p10 '((procedure start ((if north? (start) ()) turn-left start))))
(define p11 '((procedure start (turn-left turn-left turn-left))))
(define p12 '((procedure start (put-mark (if mark? (turn-left turn-left) ()) step put-mark))))
(define p13 '((procedure start (step start))))
(define p14 '((procedure start (step))))
(define p15 '((procedure start (put-mark step start))))
(define p16 '((procedure start turn-left)))
(define p17 '((procedure start get-mark)))
(define p18 '((procedure start (if wall? put-mark (step start)))))
(define p19 '((procedure start ((if wall? () (2)) put-mark))
              (procedure 2 (step start step))))
(define p20 '((procedure start (if wall? put-mark (step start turn-left turn-left step turn-left turn-left)))))
(define p21 '((procedure start () )))
(define p22 '((procedure start (if wall? put-mark step))))
(define p23 '((procedure start (if wall? (turn-left start turn-left turn-left turn-left) (if wall? put-mark (turn-left turn-left step turn-left turn-left))))))
 
 
 
  
(define (evolve pairs threshold stack-size)
  (begin
    (display (car (evaluate`(,p1 ,p2 ,p3 ,p4 ,p5 ,p6 ,p7 ,p8 ,p10 ,p11 ,p12 ,p13 ,p14 ,p15, p16 ,p17 ,p18 ,p20 ,p21 ,p22 ,p23) pairs threshold stack-size)))
   (newline)
   )
  )
 
(define (congruential-rng seed)
  (let ((a 16807 #|(expt 7 5)|#)
        (m 2147483647 #|(- (expt 2 31) 1)|#))
    (let ((m-1 (- m 1)))
      (let ((seed (+ (remainder seed m-1) 1)))
        (lambda (b)
          (let ((n (remainder (* a seed) m)))
            (set! seed n)
            (quotient (* (- n 1) b) m-1)))))))
(define random (congruential-rng 12345))
 
 
(define (mutation individual passed-parts)
  (cond
    ((eqv? (car individual) 'step) (append passed-parts (car individual) (add-new-command (congruential-rng 5) (cdr individual))))
    ((eqv? (car individual) 'turn-left)(append passed-parts (car individual) (add-new-command (congruential-rng 5) (cdr individual))))
    ((eqv? (car individual) 'put-mark) (append passed-parts (car individual) (add-new-command (congruential-rng 5) (cdr individual))))
    ((eqv? (car individual) 'get-mark) (append passed-parts (car individual) (add-new-command (congruential-rng 5) (cdr individual))))
    (#t (mutation (make-a-list(parse-procedure individual)) (cons (car individual) passed-parts)))
    )
  )
 
(define (add-new-command id array)
  (cond
    ((= id 1) (cons 'step array))
    ((= id 2) (cons 'turn-left array))
    ((= id 3) (cons 'put-mark array))
    ((= id 4) (cons 'get-mark array))
    (#t array))
  )
 
;(trace mutation)  
;(trace threshold?)
;(trace length-of-the-program)
;(trace manhattan-distance)
;(trace evaluate-programs)
;(trace evaluate-pairs)
;(trace evaluate-single-pair)
;(trace sum-thresholds)
;(trace manhattan-for-nums)
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