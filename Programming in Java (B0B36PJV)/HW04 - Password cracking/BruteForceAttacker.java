package cz.cvut.fel.pjv;

public class BruteForceAttacker extends Thief {

    public char[] charArray;
    public char[] passArr;
    public int maxIndexValue;
    public boolean endRecursion = false;

    @Override
    public void breakPassword(int sizeOfPassword) {
        charArray = getCharacters();
        passArr = new char[sizeOfPassword];
        maxIndexValue = sizeOfPassword;
        recursiveBreaker(charArray, maxIndexValue, 0);

    }

    public void recursiveBreaker(char[] chArr, int maxIdx, int index) {
        if (index == maxIdx && !endRecursion){
            if (tryOpen(passArr)) {
                endRecursion = true;
            }
        }
        else {
            for(int i = 0; i < chArr.length; i++) {
                passArr[index] = chArr[i];
                if(!endRecursion) {
                    recursiveBreaker(chArr, maxIdx, (index + 1));
                }
            }
        }
    }

}
