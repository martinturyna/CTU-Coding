#ifndef DATABASEQUERIES_QUERY_H
#define DATABASEQUERIES_QUERY_H

#include <vector>
#include <functional>
#include <atomic>

template<typename row_t>
using predicate_t = std::function<bool(const row_t &)>;



template<typename row_t>
bool is_satisfied_for_all(std::vector<predicate_t<row_t>> predicates, std::vector<row_t> data_table);

template<typename row_t>
bool is_satisfied_for_any(std::vector<predicate_t<row_t>> predicates, std::vector<row_t> data_table);



template<typename row_t>
bool is_satisfied_for_all(std::vector<predicate_t<row_t>> predicates, std::vector<row_t> data_table) {
    // Doimplementujte telo funkce, ktera rozhodne, zda pro VSECHNY dilci dotazy (obsazene ve
    // vektoru 'predicates') existuje alespon jeden zaznam v tabulce (reprezentovane vektorem
    // 'data_table'), pro ktery je dany predikat splneny.

    // Pro inspiraci si prostudujte kod, kterym muzete zjistit, zda prvni dilci dotaz plati,
    // tj., zda existuje alespon jeden radek v tabulce, pro ktery predikat reprezentovany
    // funkci predicates[i] plati:

    //auto & first_predicate = predicates[0];        // Funkce reprezentujici predikat prvniho poddotazu

    bool return_value = true;
    unsigned int row_count = data_table.size();
    unsigned int predicates_count = predicates.size();
                                                   // Kazdy radek tabulky je reprezentovany jednim prvkem
                                                   // vektoru 'data_table'. Velikost vektoru tedy odpovida
                                                   // poctu zaznamu

    #pragma omp parallel for
    for (int j = 0; j < predicates_count; j++) {

        auto & current_predicate = predicates[j];
        bool current_predicate_is_satisfied = false;

        for (unsigned int i = 0; i < row_count; i++) {
            auto &row = data_table[i]; // i-ty radek tabulky ...
            bool is_satisfied = current_predicate(row);  // ... splnuje prvni predikat, pokud funkce first_predicate
            //     vrati true

            if (is_satisfied) {
                current_predicate_is_satisfied = true;
                break;
            } else {
                // V opacnem pripade hledame dal - stale muzeme najit radek, pro ktery predikat plati
                continue;
            }
        }

        #pragma omp cancellation point for
        if (!current_predicate_is_satisfied) {
            return_value = false;
            #pragma omp cancel for
        }

        // Radek, pro ktery by prvni predikat platil, jsme nenasli. Prvni dilci dotaz je tedy
        // nepravdivy
    }

    return return_value;
}

template<typename row_t>
bool is_satisfied_for_any(std::vector<predicate_t<row_t>> predicates, std::vector<row_t> data_table) {
    // Doimplementujte telo funkce, ktera rozhodne, zda je ALESPON JEDEN dilci dotaz pravdivy.
    // To znamena, ze mate zjistit, zda existuje alespon jeden predikat 'p' a jeden zaznam
    // v tabulce 'r' takovy, ze p(r) vraci true.

    // Zamyslete se nad tim, pro ktery druh dotazu je vhodny jaky druh paralelizace. Vas
    // kod optimalizujte na situaci, kdy si myslite, ze navratova hodnota funkce bude true.
    // Je pro Vas dulezitejsi rychle najit splnujici radek pro jeden vybrany predikat, nebo
    // je dulezitejsi zkouset najit takovy radek pro vice predikatu paralelne?


    bool return_value = false;
    unsigned int row_count = data_table.size();
    unsigned int predicates_count = predicates.size();
    // Kazdy radek tabulky je reprezentovany jednim prvkem
    // vektoru 'data_table'. Velikost vektoru tedy odpovida
    // poctu zaznamu

#pragma omp parallel for
    for (int j = 0; j <row_count; j++) {

        auto & current_row = data_table[j];
        bool current_predicate_is_satisfied = false;

        for (unsigned int i = 0; i < predicates_count; i++) {
            auto & current_predicate = predicates[i];
            bool is_satisfied = current_predicate(current_row);

            if (is_satisfied) {
                current_predicate_is_satisfied = true;
                break;
            } else {
                continue;
            }
        }

        #pragma omp cancellation point for
        if (current_predicate_is_satisfied) {
            return_value = true;
        #pragma omp cancel for
        }

    }

    return return_value;
}


#endif //DATABASEQUERIES_QUERY_H
