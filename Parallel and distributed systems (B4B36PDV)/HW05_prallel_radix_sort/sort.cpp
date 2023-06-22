#include "sort.h"

using namespace std;

void bucket_sort(std::vector<std::string *> &vector_to_sort, const MappingFunction &mappingFunction,
                 unsigned long alphabet_size, unsigned long string_lengths, int depth) {

    if (string_lengths == depth) {

        return;

    } else {

        vector<string *> buckets[alphabet_size];
        for (int i = 0; i < vector_to_sort.size(); i++) {

            string * s = vector_to_sort[i];
            char char_at_position = (*s).at(depth);
            int character_number = mappingFunction(char_at_position);
            buckets[character_number].push_back(s);

        }

        for (int i = 0; i < alphabet_size; i++) {
            #pragma omp task shared(buckets)
            bucket_sort(buckets[i], mappingFunction, alphabet_size, string_lengths, depth + 1);

        }

        #pragma omp taskwait
        int position = 0;
        for (int i = 0; i < alphabet_size; i++) {

            for (int j = 0; j < buckets[i].size(); j++) {

                vector_to_sort[position++] = buckets[i][j];

            }
        }
    }
}

// implementace vaseho radiciho algoritmu. Detalnejsi popis zadani najdete v "sort.h"
void radix_par(std::vector<std::string *> &vector_to_sort, const MappingFunction &mappingFunction,
               unsigned long alphabet_size, unsigned long string_lengths) {
    #pragma omp parallel
    #pragma omp single
    bucket_sort(vector_to_sort, mappingFunction, alphabet_size, string_lengths, 0);
}