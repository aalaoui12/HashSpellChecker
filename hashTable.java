package hashingspellchecker;

public class hashTable<Key, Value> {
    private int N;
    private int M = 174631; //next prime number after double the number of entries
    private Key[] keys;
    private Value[] vals;
    
    public hashTable() {
        keys = (Key[]) new Object[M];
        vals = (Value[]) new Object[M];
    }
    
    public hashTable(int M) {
        keys = (Key[]) new Object[M];
        vals = (Value[]) new Object[M];
        this.M = M;
    }
    
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }
    
    public void put(Key key, Value val) {
        if (N >= M/2) //check if table should be resized, should not trigger 
            resize(2 * M);
        
        int i;
        for(i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if(keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }
    
    public Value get(Key key) { //this checks if a word is in the dictionary
        for(int i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if(keys[i].equals(key)) {
                return vals[i];
            }
        }
        return null;
    }
        
    private void resize(int cap) { //this won't be needed unless dictionary increases size
        hashTable<Key, Value> t = new hashTable<Key, Value>(cap);
        for(int i = 0; i < M; i++) {
            if (keys[i] != null)
                t.put(keys[i], vals[i]);
        }
        
        keys = t.keys;
        vals = t.vals;
        M = t.M;
    }
}
