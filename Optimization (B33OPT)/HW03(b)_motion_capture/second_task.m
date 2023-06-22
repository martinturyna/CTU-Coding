%Walk distances
A = load('walk1.txt');
rank(A, 1)
rank(A, 2)
rank(A, 5)
rank(A, 10)
rank(A, 15)

%Walk Graphs
A_walk = load('walk1.txt');
mean_value = mean(A_walk);
A_moved = A_walk - mean_value;
[U,S,V] = svd(A_moved);
%Walk 2D graph -> V(:,1:2)
figure
res = A_moved*V(:,1:2);
plot(res(:,1), res(:,2));
%Walk 3D graph -> V(:,1:3)
figure
res = A_moved*V(:,1:3);
plot3(res(:,1), res(:,2), res(:,3));

%Makarena Graphs
A_makarena = load('makarena1.txt');
mean_value = mean(A_makarena);
A_moved = A_makarena - mean_value;
[U,S,V] = svd(A_moved);
%Makarena 2D graph -> V(:,1:2)
figure
res = A_moved*V(:,1:2);
plot(res(:,1), res(:,2));
%Makarena 3D graph -> V(:,1:3)
figure
res = A_moved*V(:,1:3);
plot3(res(:,1), res(:,2), res(:,3));