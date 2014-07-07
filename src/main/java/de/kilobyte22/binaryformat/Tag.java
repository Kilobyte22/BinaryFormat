package de.kilobyte22.binaryformat;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class Tag {

    protected final ReadWriteLock lock = new ReentrantReadWriteLock();

    public abstract void load(DataInput input) throws IOException;
    public abstract void save(DataOutput output) throws IOException;

    public static void writeTag(DataOutput output, Tag tag) throws IOException {
        output.writeByte(tag.getType().ordinal());
        tag.save(output);
    }

    public static Tag readTag(DataInput input) throws IOException {
        TagType type = TagType.values()[input.readUnsignedByte()];
        Tag tag = makeTag(type);
        tag.load(input);
        return tag;
    }

    private static Tag makeTag(TagType type) {
        switch (type) {
            case TAG_STRING:
                return new StringTag();
            case TAG_HASH:
                return new HashTag();
            case TAG_BLOB:
                return new BlobTag();
            case TAG_INT:
                return new IntTag();
            case TAG_LIST:
                return new ListTag();
            case TAG_UNSIGNED_BYTE:
                return new UnsignedByteTag();
            case TAG_UNSIGNED_SHORT:
                return new UnsignedShortTag();
        }
        return null;
    }

    protected abstract TagType getType();

    public enum TagType {
        TAG_STRING,
        TAG_LIST,
        TAG_BLOB,
        TAG_HASH,
        TAG_INT,
        TAG_UNSIGNED_SHORT,
        TAG_UNSIGNED_BYTE
    }
}
