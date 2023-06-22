clc; clear;

b = [0 0 0];
beq = 3000;
 
% Puvodni
f = -[0 0 0 0 0 1];
A = -[1.27 1.02 0 0 0 -1
    0 1.02 4.7 3.09 0 -1
    0 0 0 3.09 9 -1];
lb = [0 0 0 0 0 2000];
Aeq = [1 1 1 1 1 0];

x = linprog(f, A, b, Aeq, beq, lb, [])

% Modifikovana
f = -[0 0 0 1];
A = -[1.27 0   0 -1
      0    4.7 0 -1
      0    0   9 -1];
Aeq = [1 1 1 0];

lb = [400 400 400 2000];

x = linprog(f, A, b, Aeq, beq, lb, [])


