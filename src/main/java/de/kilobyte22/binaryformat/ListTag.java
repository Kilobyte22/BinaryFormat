package de.kilobyte22.binaryformat;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ListTag extends Tag implements Iterable<Tag> {

    private final List<Tag> list;

    public ListTag() {
        list = new LinkedList<Tag>();
    }

    @Override
    public Iterator<Tag> iterator() {
        return list.iterator();
    }

    public ListTag add(Tag element) {
        lock.writeLock().lock();
        list.add(element);
        lock.writeLock().unlock();
        return this;
    }

    public ListTag addString(String string) {
        return add(new StringTag(string));
    }

    @Override
    public void load(DataInput input) throws IOException {
        lock.writeLock().lock();
        list.clear();
        int len = input.readInt();
        for (int i = 0; i < len; i++) {
            list.add(readTag(input));
        }
        lock.writeLock().unlock();
    }

    @Override
    public void save(DataOutput output) throws IOException {
        lock.readLock().lock();
        output.writeInt(list.size());
        for(Tag t : list) {
            writeTag(output, t);
        }
        lock.readLock().lock();
    }

    @Override
    protected TagType getType() {
        return TagType.TAG_LIST;
    }

    public int size() {
        return list.size();
    }
}
