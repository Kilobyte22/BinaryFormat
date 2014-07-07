package de.kilobyte22.binaryformat;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class UnsignedByteTag extends Tag {

    private int value;

    public UnsignedByteTag() {
        this(0);
    }

    public UnsignedByteTag(int value) {

        this.value = value;
    }

    @Override
    public void load(DataInput input) throws IOException {
        lock.writeLock().lock();
        value = input.readUnsignedByte();
        lock.writeLock().unlock();
    }

    @Override
    public void save(DataOutput output) throws IOException {
        lock.readLock().lock();
        output.writeByte(value);
        lock.readLock().unlock();
    }

    @Override
    protected TagType getType() {
        return TagType.TAG_UNSIGNED_BYTE;
    }

    public int value() {
        return value;
    }
}
