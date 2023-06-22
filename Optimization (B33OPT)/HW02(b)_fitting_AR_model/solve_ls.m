function [x] = solve_ls(A, b)
% function [x] = solve_ls(A, b)
%
% implements A\b using QR decomposition.
%
% INPUT: 
% A: an M-by-N matrix. It is assumed that that M>=N and 
% that A has full rank (=N). 
%
% b: an M-by-1 vector
%
% OUTPUT: 
% x: an N-by-1 vector, solving Ax=b in LSQ sense.

% discard the code from here and implement the functionality:
[Q,R] = qr(A,0);
Rx = Q' * b;
for i = length(Rx):-1:1
    if i == length(Rx)
        x(i,1) = Rx(i);
    else
        x(i,1) = (Rx(i) - R(i,i+1:length(Rx)) * x(i+1:length(Rx),1));
    end
    x(i,1) = x(i,1) / R(i,i);
end