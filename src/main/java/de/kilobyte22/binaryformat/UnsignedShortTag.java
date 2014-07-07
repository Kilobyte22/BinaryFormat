package de.kilobyte22.binaryformat;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class UnsignedShortTag extends Tag {

    private int value;

    public UnsignedShortTag() {
        this(0);
    }

    public UnsignedShortTag(int value) {

        this.value = value;
    }

    @Override
    public void load(DataInput input) throws IOException {
        lock.writeLock().lock();
        value = input.readUnsignedShort();
        lock.writeLock().unlock();
    }

    @Override
    public void save(DataOutput output) throws IOException {
        lock.readLock().lock();
        output.writeShort(value);
        lock.readLock().unlock();
    }

    @Override
    protected TagType getType() {
        return TagType.TAG_UNSIGNED_SHORT;
    }
}
