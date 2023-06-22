function X = rank(A, r)
    A = load('walk1.txt');
    mean_value = mean(A);
    A_moved = A - mean_value;
    [U,S,V] = svd(A_moved);
    projection = V(:,1:r)*V(:,1:r)';
    projected_moved_points = A_moved*projection;
    projected_points = projected_moved_points + mean_value;
    dist = sum(vecnorm(A-projected_points).^2)
end


