package com.techzealot.collection.tree.trie

import spock.lang.Specification

class SimpleCharTrieTest extends Specification {


    def "test contains"() {
        when:
        def trie = new SimpleCharTrie<Integer>()
        then:
        !trie.contains("any")
        when:
        trie.add("abc", 1)
        trie.add("b", 2)
        trie.add("cat", 3)
        then:
        trie.contains("abc")
        trie.contains("b")
        trie.contains("cat")
        !trie.contains("a")
        !trie.contains("ab")
        !trie.contains("ca")
        !trie.contains("not exist")
    }

    def "test add and size"() {
        given:
        def trie = new SimpleCharTrie<Integer>()
        when:
        def size = trie.size()
        then:
        size == 0
        when:
        trie.add("abc", 1)
        trie.add("b", 2)
        trie.add("cat", 3)
        then:
        trie.size() == 3
    }

    def "test remove"() {
        when:
        def trie = new SimpleCharTrie<Integer>()
        then:
        trie.remove("not found") == null
        trie.size() == 0
        when:
        trie.add("abc", 1)
        trie.add("b", 2)
        trie.add("cat", 3)
        trie.add("ace", 4)
        trie.add("ab", 5)
        trie.add("ac", 6)
        int size = 6
        then:
        trie.keyList() == ["ab", "abc", "ac", "ace", "b", "cat"]
        trie.size() == size
        trie.remove("a") == null
        trie.size() == size
        trie.remove("ca") == null
        trie.size() == size
        trie.remove("xx") == null
        trie.size() == size
        //先短后长
        trie.remove("ab") == 5
        !trie.contains("ab")
        trie.keyList() == ["abc", "ac", "ace", "b", "cat"]
        trie.size() == --size
        trie.remove("abc") == 1
        trie.keyList() == ["ac", "ace", "b", "cat"]
        !trie.contains("abc")

        trie.size() == --size
        trie.remove("b") == 2
        trie.keyList() == ["ac", "ace", "cat"]
        !trie.contains("b")
        trie.size() == --size
        trie.remove("cat") == 3
        trie.keyList() == ["ac", "ace"]
        !trie.contains("cat")
        trie.size() == --size
        //先长后短
        trie.remove("ace") == 4
        trie.keyList() == ["ac"]
        !trie.contains("ace")
        trie.size() == --size
        trie.remove("ac") == 6
        trie.keyList() == []
        !trie.contains("ac")
        trie.size() == --size

        trie["root"] == null
    }

    def "test startWith"() {
        when:
        def trie = new SimpleCharTrie<Integer>()
        then:
        !trie.startWith("any")
        when:
        trie.add("abc", 1)
        trie.add("b", 2)
        trie.add("cat", 3)
        then:
        trie.keyList() == ["abc", "b", "cat"]
        trie.startWith("a")
        trie.startWith("ab")
        trie.startWith("abc")
        trie.startWith("b")
        trie.startWith("c")
        trie.startWith("ca")
        trie.startWith("cat")
        !trie.startWith("not")
        !trie.startWith("x")
    }

    def "test string"() {
        given:
        def trie = new SimpleCharTrie<Integer>()
        when:
        trie.add("abc", 1)
        trie.add("b", 2)
        trie.add("cat", 3)
        trie.add("ace", 4)
        trie.add("ab", 5)
        trie.add("ac", 6)
        then:
        trie.keyList() == ["ab", "abc", "ac", "ace", "b", "cat"]
    }
}
