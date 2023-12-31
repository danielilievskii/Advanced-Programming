Потребно е да се развие генерички систем за статистичка обработка на броеви.

За да се обработат броевите ќе се користи генеричкиот интерфејс NumberProcessor. Овој интерфејс има само еден метод со потпис: R compute(ArrayList<T> numbers), каде што R е типот на резултатот од методот, a numbers e листа од броеви (било каков тип на броеви).

Дадена е класата Numbers во која ќе се чуваат листа од било какви броеви. За класата да се дефинираат соодветните генерички параметри, да се имплементира конструктор Numbers(ArrayList<T> elements), како и да се комплетира имплементацијата на методот process кој како аргумент прима објект од типот NumberProcessor.

Дополнително, во главната програма потребно е да се имплементираат четири конкретни обработувачи на броеви NumberProcessor (со помош на ламбда изрази или анонимни класи). Овие имплементации ќе ги користат објекти од класата Numbers за обработување на своите елементи:

Процесор кој ќе врати колку броеви се негативни
Процесор којшто ќе врати дескриптивни статистики (count, min, average, max) за броевите
Процесор којшто ќе ги врати броевите сортирани во растечки редослед.
Процесор којшто ќе ја пресмета медијаната на броевите (елементот на средина во сортирана листа доколку листата има непарен број на елементи, а во спротивно просекот на двата средни елементи, доколку листата има парен број на елементи).