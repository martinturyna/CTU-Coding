#include "bfs.h"
#include <unordered_set>
#include <queue>
#include <iostream>
#include "omp.h"
#include "climits"

// Naimplementujte efektivni algoritmus pro nalezeni nejkratsi cesty v grafu.
// V teto metode nemusite prilis optimalizovat pametove naroky, a vhodnym algo-
// ritmem tak muze byt napriklad pouziti prohledavani do sirky (breadth-first
// search.
//
// Metoda ma za ukol vratit ukazatel na cilovy stav, ktery je dosazitelny pomoci
// nejkratsi cesty.

std::shared_ptr<const state> bfs(std::shared_ptr<const state> root) {

    std::vector<std::shared_ptr<const state>> queue;
    std::vector<std::shared_ptr<const state>> queue_help;
    std::unordered_set<unsigned long long> visited_set;

    queue.push_back(root);
    visited_set.insert(root->get_identifier());

    std::shared_ptr<const state> result = nullptr;

    while(!queue.empty()) {

        auto queue_size = queue.size();
        queue_help.clear();

        #pragma omp parallel for
        for (int i = 0; i < queue_size; i++) {

            auto current_node = queue[i];
            std::vector<std::shared_ptr<const state>> neighbours = current_node->next_states();

            for (int j = 0; j < neighbours.size(); j++) {
                auto tmp_node = neighbours[j];

                if (visited_set.count(tmp_node->get_identifier()) == 0) {

                    if (tmp_node->is_goal()) {

                        if (result == nullptr || result->get_identifier() > tmp_node->get_identifier()) {
                            result = tmp_node;
                        }

                    } else {
                        #pragma omp critical
                        {
                            visited_set.insert(tmp_node->get_identifier());
                            queue_help.push_back(tmp_node);
                        }
                    }
                }
            }
        }

        if (result != nullptr) {
            return result;
        }
        // Heckin solution of seg fault ...
        queue.swap(queue_help);
    }
    return nullptr;
}