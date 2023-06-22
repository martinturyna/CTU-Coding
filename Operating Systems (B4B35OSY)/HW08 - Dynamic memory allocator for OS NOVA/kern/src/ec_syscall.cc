#include "ec.h"
#include "kalloc.h"
#include "ptab.h"



typedef enum {
    sys_print      = 1,
    sys_sum        = 2,
    sys_break      = 3,
    sys_thr_create = 4,
    sys_thr_yeild  = 5,
} Syscall_numbers;

void Ec::syscall_handler (uint8 a)
{
    // Get access to registers stored during entering the system - see
    // entry_sysenter in entry.S
    Sys_regs * r = current->sys_regs();
    Syscall_numbers number = static_cast<Syscall_numbers> (a);

    switch (number) {
        case sys_print: {
            char *data = reinterpret_cast<char*>(r->esi);
            unsigned len = r->edi;
            for (unsigned i = 0; i < len; i++)
                printf("%c", data[i]);
            break;
        }
        case sys_sum: {
            // Naprosto nepotřebné systémové volání na sečtení dvou čísel
            int first_number = r->esi;
            int second_number = r->edi;
            r->eax = first_number + second_number;
            break;
        }
        case sys_break: {
            //////////////////////////////////////////////////////////////
            /* Poznamka:  Snazil jsem se program odevzdat do limitu -2 body.
            Nicmene mi neprochazel Test#3. Snazil jsem se odhalit chybu
            co nejdrive a 2,5 hodiny po limitu jsem zjistil, ze chyba byla
            absolutne trivialni - jednalo se o spatne povolene meze.
            Pouzival jsem od 0xC0000000 do 0x1000 a spravne byl limit od pameti, 
            ktera je pouzivana aplikaci (cili od Ec::break_min) po 0x1000000.
            Pokud by slo primhourit ocko vyjimecne, budu moc rad O:)
            */

            mword final_address = r->esi;
            r->eax = Ec::break_current;
            bool smth_wrong = false;
            /* In Case of 0 return current break address */
            if (final_address == 0) {
                printf("final_address == 0\n");
                r->eax = Ec::break_current;
                break;

            }
            /* In case the address is out of allowed bounds */
            if (final_address > 0xC0000000 || final_address < Ec::break_min) {
                printf("Out of bounds\n");
                r->eax = 0;
                break;
            
            }
            
            if (final_address < Ec::break_current) {
                printf("final < curr\n");
                mword tmp_break = (Ec::break_current + PAGE_SIZE - 1) & ~PAGE_MASK;
                mword target_break = (final_address + PAGE_SIZE - 1) & ~PAGE_MASK;
                printf("Target break: %lu\n", target_break);
        
                /* In case of break in other page than current */
                int counter = 0;
                while(tmp_break != target_break) { 
                    counter++;
                    //tmp_break -= PAGE_SIZE;
                    printf("%d. address: %lu\n", counter, tmp_break);
                    void* phys2virt_addr = Kalloc::phys2virt(Ptab::get_mapping(tmp_break - PAGE_SIZE) & ~PAGE_MASK);
                    Kalloc::allocator.free_page(phys2virt_addr);
                    tmp_break -= PAGE_SIZE;
                    Ptab::insert_mapping(tmp_break, 0, 0);

                }
            
                Ec::break_current = final_address;
                break;
            }
            else if (final_address > Ec::break_current) {
                printf("final > curr\n");
                mword tmp_break = (Ec::break_current + PAGE_SIZE - 1) & ~PAGE_MASK;
                mword curr_break = (Ec::break_current + PAGE_SIZE - 1) & ~PAGE_MASK;
                mword target_break = (final_address + PAGE_SIZE - 1) & ~PAGE_MASK;

                int counter = 0;
                while (tmp_break != target_break) {
                    counter++;
                    void* page_to_map = Kalloc::allocator.alloc_page(1, Kalloc::FILL_0);
                    if (page_to_map == NULL) {
                        printf("Kalloc NULL\n");
                        while(tmp_break > curr_break) {
                            tmp_break -= PAGE_SIZE;
                            void* phys2virt_addr = Kalloc::phys2virt(Ptab::get_mapping(tmp_break) & ~PAGE_MASK);
                            Kalloc::allocator.free_page(phys2virt_addr);
                            Ptab::insert_mapping(tmp_break, 0, 0);
                        }
                        r->eax = 0;
                        // This was changed
                        smth_wrong = true;
                        break;
                        
                    }
                    else {
                        if (!Ptab::insert_mapping(tmp_break, Kalloc::virt2phys(page_to_map), Ptab::PRESENT | Ptab::RW | Ptab::USER)) {
                            Kalloc::allocator.free_page(page_to_map);
                            printf("Mapp FAIL\n");
                            r->eax = 0;
                            smth_wrong = true;
                        }
                        
                    }
                    printf("%d %lu\n", counter, tmp_break);
                    tmp_break += PAGE_SIZE;

                }

                if (smth_wrong) {
                    break;
                }

                Ec::break_current = final_address;
                break;

            }
            /* Current break is the same as the new one */
            printf("Given the same break\n");
            break;
            ///////////////////////////////////////////////////////////////
        }
        default:
            printf ("unknown syscall %d\n", number);
            break;
    };

    ret_user_sysexit();
}
