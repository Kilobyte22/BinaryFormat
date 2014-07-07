package de.kilobyte22.binaryformat;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class BlobTag extends Tag {

    private byte[] bytes;

    public BlobTag() {
        this(new byte[0]);
    }

    public BlobTag(byte[] bytes) {

        this.bytes = bytes;
    }

    @Override
    public void load(DataInput input) throws IOException {
        bytes = new byte[input.readInt()];
        input.readFully(bytes);
    }

    @Override
    public void save(DataOutput output) throws IOException {
        output.writeInt(bytes.length);
        output.write(bytes);
    }

    @Override
    protected TagType getType() {
        return TagType.TAG_BLOB;
    }

    public byte[] value() {
        return bytes;
    }
}
