# java language seed

1. [ ] java collection framework
    1. [x] ArrayList
    2. [x] LinkedList
    3. [x] ArrayDeque
    4. [x] PriorityQueue
    5. [ ] HashMap
    6. [ ] TreeMap
    7. [ ] LinkedHashMap
    8. [ ] HashSet
    9. [ ] TreeSet
    10. [ ] LinkedHashSet
    11. [ ] BinarySearchTree
    12. [x] AVLTree
    13. [x] LLRBTree
    14. [x] RBTree
    15. [ ] B-Tree
    16. [ ] B+Tree
    17. [x] Trie
    18. [x] UnionFind
    19. [ ] SuffixTree
    20. [ ] RadixTree
    21. [ ] LSMTree
    22. [x] SegmentTree
2. [ ] java util concurrent
    1. [ ] Lock
    2. [ ] ConcurrentHashMap
    3. [ ] BlockingQueue
    4. [ ] Atomic
3. [ ] java thread pool
4. [ ] graph
5. [ ] algorithm
6. [x] design patterns

# test:

带有复杂继承体系的类的测试方式

1. 使用junit:使用groovy和junit按照类的继承体系实现测试类继承体系从而达到复用,使用接口方法作为子类工厂方法
2. 使用spock:使用接口按照junit注解方式实现要测试类的所有父类的测试,使用接口方法作为子类工厂方法
3. 使用kotest:使用CompositeSpec复用父类的测试用例,使用构造参数传入子类实现

spock

JMH

JCStress

参考eclipse-collections三种测试

参考:

[算法四](https://github.com/kevin-wayne/algs4)

https://github.com/petar/GoLLRB

https://github.com/patricklaux/perfect-code

https://github.com/myui/btree4j

https://github.com/andylamp/BPlusTree

https://github.com/phishman3579/java-algorithms-implementation

https://github.com/npgall/concurrent-trees

https://github.com/eclipse/eclipse-collections

https://github.com/java-diff-utils/java-diff-utils

https://github.com/google/guava

https://github.com/jgrapht/jgrapht

https://github.com/JCTools/JCTools
