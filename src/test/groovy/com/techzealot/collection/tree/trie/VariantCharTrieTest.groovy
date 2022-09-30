package com.techzealot.collection.tree.trie


import spock.lang.Specification

class VariantCharTrieTest extends Specification {
    def "test composite case"() {
        given:
        def trie = new VariantCharTrie()
        when:
        def size = trie.size()
        then:
        size == 0
        trie.keyList() == []
        when:
        trie.put("abc", 1)
        trie.put("b", 2)
        trie.put("cat", 3)
        trie.put("ace", 4)
        trie.put("ab", 5)
        trie.put("ac", 6)
        then:
        trie.size() == 6
        then:
        trie.contains("abc")
        trie.contains("b")
        trie.contains("cat")
        trie.contains("ace")
        trie.contains("ab")
        trie.contains("ac")
        !trie.contains("xx")
        !trie.contains("")
        !trie.contains("ca")
        !trie.contains("a")
        !trie.contains(null)
        then:
        trie.get("abc") == 1
        trie.get("b") == 2
        trie.get("cat") == 3
        trie.get("ace") == 4
        trie.get("ab") == 5
        trie.get("ac") == 6
        trie.get("xx") == null
        trie.get("a") == null
        trie.get("ca") == null
        trie.get(null) == null
        then:
        !trie.startWith("")
        !trie.startWith("xx")
        trie.startWith("a")
        trie.startWith("ab")
        trie.startWith("abc")
        !trie.startWith("bc")
        trie.startWith("b")
        trie.startWith("c")
        trie.startWith("ca")
        !trie.startWith("at")
        trie.startWith("cat")
        trie.startWith("ac")
        !trie.startWith("ce")
        trie.startWith("ace")
        when:
        trie.put("cat", 7)
        trie.put("ace", 8)
        then: "测试put覆盖"
        trie.size() == 8
        trie.get("cat") == 7
        trie.get("ace") == 8
        then:
        trie.countWord("cat") == 2
        trie.countWord("ace") == 2
        trie.countWord("abc") == 1
        trie.countWord("ab") == 1
        trie.countWord("b") == 1
        trie.countWord("ac") == 1
        trie.countWord("") == 0
        trie.countWord("xx") == 0
        trie.countWord(null) == 0
        then:
        trie.countPrefix("a") == 5
        trie.countPrefix("ab") == 2
        trie.countPrefix("abc") == 1
        trie.countPrefix("ab") == 2
        trie.countPrefix("ab") == 2
        trie.countPrefix("bc") == 0
        trie.countPrefix("b") == 1
        trie.countPrefix("ac") == 3
        trie.countPrefix("ace") == 2
        trie.countPrefix("cat") == 2
        trie.countPrefix("") == 0
        trie.countPrefix("xx") == 0
        trie.countPrefix(null) == 0
        then:
        trie.keyList() == ["ab", "abc", "ac", "ace", "b", "cat"]
        then:
        trie.searchPrefix("a") == ["ab": 5, "abc": 1, "ac": 6, "ace": 8]
        trie.searchPrefix("ab") == ["ab": 5, "abc": 1]
        trie.searchPrefix("abc") == ["abc": 1]
        trie.searchPrefix("ac") == ["ac": 6, "ace": 8]
        trie.searchPrefix("ace") == ["ace": 8]
        trie.searchPrefix("b") == ["b": 2]
        trie.searchPrefix("c") == ["cat": 7]
        trie.searchPrefix("ca") == ["cat": 7]
        trie.searchPrefix("cat") == ["cat": 7]
        then: "remove"
        trie.remove("cat") == 7
        trie.remove("abc") == 1
        trie.remove("ac") == 6
        then:
        trie.size() == 4
        trie.keyList() == ["ab", "ace", "b"]
        trie.countWord("a") == 0
        trie.countPrefix("a") == 3
        trie.countWord("ab") == 1
        trie.countPrefix("ab") == 1
        trie.countWord("ac") == 0
        trie.countPrefix("ac") == 2
        trie.countWord("ace") == 2
        trie.countPrefix("ace") == 2
        trie.countWord("b") == 1
        trie.countPrefix("b") == 1
        then:
        trie.remove("ac") == null
    }
}
