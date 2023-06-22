clear; clc;
load('data1.mat');
scatter(x, y, 'kx');
hold on
xlabel('x')
ylabel('y')

A = -[-x -ones(m,1) ones(m,1)
    x ones(m,1) ones(m,1)];
b = -[-y; y];
f = [0 0 1];

res = linprog(f, A, b)

plot(linspace(0,1,m), res(1)*linspace(0,1,m) + res(2) + res(3), 'r--')
plot(linspace(0,1,m), res(1)*linspace(0,1,m) + res(2), 'r')
plot(linspace(0,1,m), res(1)*linspace(0,1,m) + res(2) - res(3), 'r--')

hold off



