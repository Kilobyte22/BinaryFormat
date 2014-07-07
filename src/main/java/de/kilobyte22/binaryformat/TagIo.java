package de.kilobyte22.binaryformat;

import java.io.DataInput;
import java.io.IOException;

public class TagIo {
    public static HashTag readHashTag(DataInput input) throws IOException {
        HashTag tag = new HashTag();
        tag.load(input);
        return tag;
    }

    public static ListTag readListTag(DataInput input) throws IOException {
        ListTag tag = new ListTag();
        tag.load(input);
        return tag;
    }
}
