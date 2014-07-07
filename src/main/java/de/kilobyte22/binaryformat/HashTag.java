package de.kilobyte22.binaryformat;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HashTag extends Tag implements Iterable<Pair<String, Tag>>{

    protected final Map<String, Tag> data;

    public HashTag() {
        data = new HashMap<String, Tag>();
    }

    public HashTag put(String key, Tag value) {
        lock.writeLock().lock();
        data.put(key, value);
        lock.writeLock().unlock();
        return this;
    }

    @Override
    public void load(DataInput input) throws IOException {
        lock.writeLock().lock();
        data.clear();
        int count = input.readInt();
        for (int i = 0; i < count; i++) {
            String key = input.readUTF();
            Tag value = readTag(input);
            data.put(key, value);
        }
        lock.writeLock().unlock();
    }

    @Override
    public void save(DataOutput output) throws IOException {
        lock.readLock().lock();
        output.writeInt(data.size());
        for (String key : data.keySet()) {
            output.writeUTF(key);
            writeTag(output, data.get(key));
        }
        lock.readLock().unlock();
    }

    @Override
    protected TagType getType() {
        return TagType.TAG_HASH;
    }

    @Override
    public Iterator<Pair<String, Tag>> iterator() {
        final Iterator<String> it = data.keySet().iterator();
        return new Iterator<Pair<String, Tag>>() {
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public Pair<String, Tag> next() {
                lock.readLock().lock();
                String key = it.next();
                Pair<String, Tag> ret = new Pair<String, Tag>(key, data.get(key));
                lock.readLock().unlock();
                return ret;
            }

            @Override
            public void remove() {
                it.remove();
            }
        };
    }

    public Tag get(String name) {
        lock.readLock().lock();
        Tag ret = data.get(name);
        lock.readLock().unlock();
        return ret;
    }

    public int getInt(String name) {
        Tag t = get(name);
        if (t == null)
            throw new NullPointerException("Unknown key " + name);
        if (t instanceof IntTag) {
            return ((IntTag) t).value();
        } else {
            throw new ClassCastException(name + " is of type " + t.getType().name() + ", not TAG_INT");
        }
    }

    public int getUByte(String name) {
        Tag t = get(name);
        if (t == null)
            throw new NullPointerException("Unknown key " + name);
        if (t instanceof UnsignedByteTag) {
            return ((UnsignedByteTag) t).value();
        } else {
            throw new ClassCastException(name + " is of type " + t.getType().name() + ", not TAG_UNSIGNED_BYTE");
        }
    }

    public int getUByte(String name, int def) {
        Tag t = get(name);
        if (t == null)
            return def;
        if (t instanceof UnsignedByteTag) {
            return ((UnsignedByteTag) t).value();
        } else {
            throw new ClassCastException(name + " is of type " + t.getType().name() + ", not TAG_UNSIGNED_BYTE");
        }
    }

    public String getString(String name) {
        Tag t = get(name);
        if (t == null)
            throw new NullPointerException("Unknown key " + name);
        if (t instanceof StringTag) {
            return ((StringTag) t).value();
        } else {
            throw new ClassCastException(name + " is of type " + t.getType().name() + ", not TAG_STRING");
        }
    }

    public ListTag getList(String name) {
        Tag t = get(name);
        if (t == null)
            throw new NullPointerException("Unknown key " + name);
        if (t instanceof ListTag) {
            return (ListTag) t;
        } else {
            throw new ClassCastException(name + " is of type " + t.getType().name() + ", not TAG_LIST");
        }
    }

    public byte[] getByteArray(String name) {
        Tag t = get(name);
        if (t == null)
            throw new NullPointerException("Unknown key " + name);
        if (t instanceof BlobTag) {
            return ((BlobTag) t).value();
        } else {
            throw new ClassCastException(name + " is of type " + t.getType().name() + ", not TAG_LIST");
        }
    }

    public HashTag putUByte(String key, int value) {
        return put(key, new UnsignedByteTag(value));
    }

    public HashTag putString(String key, String value) {
        return put(key, new StringTag(value));
    }

    public HashTag putByteArray(String key, byte[] value) {
        return put(key, new BlobTag(value));
    }


}
