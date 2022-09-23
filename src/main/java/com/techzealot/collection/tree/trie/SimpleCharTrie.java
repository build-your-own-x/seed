package com.techzealot.collection.tree.trie;

import java.util.Map;

public class SimpleCharTrie implements CharTrie {

    private class Node {
        //是否单词结尾
        boolean isWord;
        //子节点映射,数据存储在连接线中而非节点中
        Map<Character, Node> next;
    }
}
