function x = wages_fit_model(t, M)
% function x = wages_fit_model(t, M)
%
% INPUT: N data points specified by
% t : N-by-1 vector, years
% M : N-by-1 vector, wages
%
% OUTPUT:
% x: 2-by-1 vector specifying the estimated model 
% M(t) = x[1] + x[2]*t

% discard the code from here and implement the functionality:
Data = load('mzdy.txt', '-ascii');
t = Data(:, 1); % years
M = Data(:, 2); % wages

A = [ones(size(t)), t];
x = A \ M;




