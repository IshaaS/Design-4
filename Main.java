// https://leetcode.com/discuss/interview-question/341818


// Time Complexity : O(1), O(n) (specified w fucntions)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this :no


// Your code here along with comments explaining your approach
// we use hashmap to store elements to skip with their frequency
// when skip is called, if it is same as nextelement we just change our next element
// and if it is not same we add it/increase its frquency in the hash map
// advance=> it basically sets the next element (by checking if next of native is supposed to be
// skipped or not)


class SkipIterator implements Iterator<Integer> {
   
    Integer nextEl;
    Iterator<Integer> nativeI;
    HashMap<Integer, Integer> map;
    public SkipIterator(Iterator<Integer> it) {
        this.nativeI=it;
        this.map = new HashMap<>();
        advance();
    }
    private void advance(){
        this.nextEl=null;
        while(nextEl==null && nativeI.hasNext()){
            Integer element = nativeI.next();
            if(map.containsKey(element)){
                Integer freq= map.get(element);
                if(freq==1) map.remove(element);
                else map.put(element, freq-1 );
            }else nextEl=element;
        }
    }
    @Override
    public boolean hasNext() {
       return nextEl!=null;
    }

    @Override
    public Integer next() {
        Integer result = nextEl;
        advance();
        return result;
    }

    public void skip(int num) {
        if(num==nextEl) advance();
        else{
            map.put(num, map.getOrDefault(num,0)+1);
        }
    }
}

public class Main {
        public static void main(String[] args) {
            SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
            System.out.println(itr.hasNext()); // true
            System.out.println(itr.next()); // returns 2
            itr.skip(5);
            System.out.println(itr.next()); // returns 3
            System.out.println(itr.next()); // returns 6 because 5 should be skipped
            System.out.println(itr.next()); // returns 5
            itr.skip(5);
            itr.skip(5);
            System.out.println(itr.next()); // returns 7
            System.out.println(itr.next()); // returns -1
            System.out.println(itr.next()); // returns 10
            System.out.println(itr.hasNext()); // false
    }
}