load('volby_2017.mat');

[U, S, V] = svd(T.data - (sum(T.data, 2) / size(T.data, 2)));
result = T.data' * U(:, 1:2);
optval = diag(S).^2;
sum(optval(3:end))

figure
hold on
title('Politicke spektrum na zaklade odpovedi')
scatter(result(:, 1), result(:, 2), 100, cell2mat(T.color(:)), 'filled');
text(result(:, 1), result(:, 2), T.strana_zkratka);
hold off