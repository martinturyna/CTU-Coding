function [x_new] = make_GN_iter(x, a)
% function [x_new] = make_GN_iter(x, a)
%
% makes the Gauss-Newton iteration. 
%
% INPUT:
% x, a   are as usual (see dist.m for explanation) 
%
% x_new is the updated x. 
   
 % discard the code from here and implement the functionality: 
 
Jakobian = [derivate(x, a, 1), derivate(x, a, 2), -ones(size(a,2),1)];
x_new = x - ((Jakobian'*Jakobian)^(-1))*Jakobian' * dist(x, a);

end

function d = derivate(x, a, Q)
    d = zeros(size(a,2),1);
    for i = 1:size(a,2)
        d(i) = (x(Q) - a(Q,i))/sqrt((x(1) - a(1,i))^2 + (x(2) - a(2,i))^2);
    end
end
    

