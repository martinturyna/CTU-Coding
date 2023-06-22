import java.util.ArrayList;
import java.util.List;

interface OMOSetView {
    boolean contains(int element);

    int[] toArray();

    OMOSetView copy();
}

class OMOSet implements OMOSetView {

    List<Integer> arrayList = new ArrayList<Integer>();

    public void add(int element) {
        if (!arrayList.contains(element)) {
            arrayList.add(element);
        }
    }

    public void remove(int element) {
        Integer elem = element;
        arrayList.remove(elem);
    }

    @Override
    public boolean contains(int element) {
        return arrayList.contains(element);
    }

    @Override
    public int[] toArray() {
        int[] tmpArray = new int[arrayList.size()];
        int i = 0;
        for (Integer element : arrayList) {
            tmpArray[i++] = element;
        }
        return tmpArray;
    }

    @Override
    public OMOSetView copy() {

        OMOSet deepCopy = new OMOSet();

        for(Integer element : arrayList) {
            deepCopy.add(element);
        }
        return deepCopy;
    }

}

class OMOSetUnion implements OMOSetView {

    OMOSetView A, B;

    OMOSetUnion(OMOSetView setA, OMOSetView setB) {

        A = setA;
        B = setB;

    }


    @Override
    public boolean contains(int element) {

        return (A.contains(element) || B.contains(element));
    }

    @Override
    public int[] toArray() {
        List<Integer> arrayList = new ArrayList<Integer>();

        for (int element : A.toArray()) {
            arrayList.add(element);
        }
        for (int element : B.toArray()) {
            if(!arrayList.contains(element)) {
                arrayList.add(element);
            }
        }

        int[] tmpArray = new int[arrayList.size()];
        int i = 0;
        for (Integer element : arrayList) {
            tmpArray[i++] = element;
        }
        return tmpArray;
    }

    @Override
    public OMOSetView copy() {

        OMOSet deepCopy = new OMOSet();

        for(Integer element : this.toArray()) {
            deepCopy.add(element);
        }
        return deepCopy;
    }
}

class OMOSetIntersection implements OMOSetView {

    OMOSetView A, B;

    OMOSetIntersection(OMOSetView setA, OMOSetView setB) {
        A = setA;
        B = setB;
    }

    @Override
    public boolean contains(int element) {
        return (A.contains(element) && B.contains(element));
    }

    @Override
    public int[] toArray() {

        List<Integer> arrayList = new ArrayList<Integer>();

        for (int element : A.toArray()) {
            if(B.contains(element)) {
                arrayList.add(element);
            }
        }

        int[] tmpArray = new int[arrayList.size()];
        int i = 0;
        for (Integer element : arrayList) {
            tmpArray[i++] = element;
        }
        return tmpArray;
    }

    @Override
    public OMOSetView copy() {

        OMOSet deepCopy = new OMOSet();

        for(Integer element : this.toArray()) {
            deepCopy.add(element);
        }
        return deepCopy;
    }
}

class OMOSetComplement implements OMOSetView {

    OMOSetView A, B;

    OMOSetComplement(OMOSetView setA, OMOSetView setB) {

        A = setA;
        B = setB;

    }

    @Override
    public boolean contains(int element) {
        return (A.contains(element) && !B.contains(element));
    }

    @Override
    public int[] toArray() {

        List<Integer> arrayList = new ArrayList<Integer>();

        for (int element : A.toArray()) {
            if(this.contains(element)) {
                arrayList.add(element);
            }
        }

        int[] tmpArray = new int[arrayList.size()];
        int i = 0;
        for (Integer element : arrayList) {
            tmpArray[i++] = element;
        }
        return tmpArray;
    }

    @Override
    public OMOSetView copy() {

        OMOSet deepCopy = new OMOSet();

        for(Integer element : this.toArray()) {
            deepCopy.add(element);
        }
        return deepCopy;
    }
}

class OMOSetEven implements OMOSetView {

    OMOSetView A;

    OMOSetEven(OMOSetView setA) {

        A = setA;

    }

    @Override
    public boolean contains(int element) {
        return (A.contains(element) && ((element % 2) == 0));
    }

    @Override
    public int[] toArray() {

        List<Integer> arrayList = new ArrayList<Integer>();

        for (int element : A.toArray()) {
            if((element % 2) == 0) {
                arrayList.add(element);
            }
        }

        int[] tmpArray = new int[arrayList.size()];
        int i = 0;
        for (Integer element : arrayList) {
            tmpArray[i++] = element;
        }
        return tmpArray;
    }

    @Override
    public OMOSetView copy() {

        OMOSet deepCopy = new OMOSet();

        for(Integer element : this.toArray()) {
            deepCopy.add(element);
        }
        return deepCopy;
    }
}