load('data_A.mat');
mean_value = mean(A);
A_moved = A - mean_value;
[U,S,V] = svd(A_moved);
projection = V(:,1)*V(:,1)';
projected_moved_points = A_moved*projection;
projected_points = projected_moved_points + mean_value;

scatter(A(:,1),A(:,2), 'x', 'blue');
hold on;
scatter(projected_points(:,1),projected_points(:,2),'x', 'red');

sum_squares = A - projected_points;
sum_squares = sum_squares.^2;
sum_squares = sum(sum(sum_squares, 2))

s = V(:,1) %smìrový vektor
x = V(:,2) %normálový vektor
y0 = mean_value' - projection*mean_value'
alfa = -x'*y0



