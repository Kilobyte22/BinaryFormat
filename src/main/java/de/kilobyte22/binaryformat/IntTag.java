package de.kilobyte22.binaryformat;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class IntTag extends Tag {

    private int value;

    public IntTag() {
        this(0);
    }

    public IntTag(int value) {

        this.value = value;
    }

    @Override
    public void load(DataInput input) throws IOException {
        lock.writeLock().lock();
        value = input.readInt();
        lock.writeLock().unlock();
    }

    @Override
    public void save(DataOutput output) throws IOException {
        lock.readLock().lock();
        output.writeInt(value);
        lock.readLock().unlock();
    }

    @Override
    protected TagType getType() {
        return TagType.TAG_INT;
    }

    public int value() {
        return value;
    }
}
