#include <functional>
#include "bst_tree.h"

void bst_tree::insert(long long data) {
    node * new_node = new node(data);

    // Naimplementujte zde vlaknove-bezpecne vlozeni do binarniho vyhledavaciho stromu
    node * current_node = root;
    while(true) {
        node * next = nullptr;

        if (current_node == nullptr) {
            if (root.compare_exchange_strong(current_node, new_node)) return;
        } else if (current_node->data >= data) {
            next = current_node->left;
            if (next == nullptr) {
                if (current_node->left.compare_exchange_strong(next, new_node)) return;
            } else {
                current_node = current_node->left;
            }
        } else {
            next = current_node->right;
            if (next == nullptr) {
                if (current_node->right.compare_exchange_strong(next, new_node)) return;
            } else {
                current_node = current_node->right;
            }
        }
    }
}

bst_tree::~bst_tree() {
    // Rekurzivni funkce pro pruchod stromu a dealokaci pameti prirazene jednotlivym
    // uzlum
    std::function<void(node*)> cleanup = [&](node * n) {
        if(n != nullptr) {
            cleanup(n->left);
            cleanup(n->right);

            delete n;
        }
    };
    cleanup(root);
}
