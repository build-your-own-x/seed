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

        then:
        trie.size() == 5
        trie.remove("a") == null
        trie.size() == 5
        trie.remove("ac") == null
        trie.size() == 5
        trie.remove("xx") == null
        trie.size() == 5
        trie.remove("ab") == 5
        trie.size() == 4
        trie.remove("abc") == 1
        trie.size() == 3
        trie.remove("b") == 2
        trie.size() == 2
        trie.remove("cat") == 3
        trie.size() == 1
        trie.remove("ace") == 4
        trie.size() == 0
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
}
