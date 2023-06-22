function [x_new, success] = make_LM_iter(x, a, mu)
% function [x_new, success] = make_LM_iter(x, a, mu)
% 
% makes the Levenberg-Marquardt iteration. 
%
% INPUT:
% x, a   are as usual (see dist.m for explanation) 
% mu is the damping factor (the factor which multiplies the
% regularizing identity matrix)
%
% OUTPUT: 
% success == 1 if the iteration is successful, i.e. 
% value of criterion f is decreased after the update 
% of x. 
% success == 0 in the oposite case. 
%
% x_new is the updated x if success == 1. 
% x_new is set equal to the input x if success == 0. 
    
% discard the code from here and implement the functionality: 

Jakobian = [derivate(x, a, 1), derivate(x, a, 2), -ones(size(a,2),1)];

x_new = x - ((Jakobian'*Jakobian + mu*eye(3))^(-1))*Jakobian' * dist(x, a);

success = norm(dist(x_new, a)) < norm(dist(x, a));

end

function d = derivate(x, a, Q)
    d = zeros(size(a,2),1);
    for i = 1:size(a,2)
        d(i) = (x(Q) - a(Q,i))/sqrt((x(1) - a(1,i))^2 + (x(2) - a(2,i))^2);
    end
end
    