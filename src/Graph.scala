
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.graphx.{Edge, EdgeContext, Graph, _}


class node(var id: Int, var monster: Monster) {

}

object Graphh {
  val conf = new SparkConf()
    .setAppName("Fight")
    .setMaster("local[*]")
  val sc = new SparkContext(conf)
  sc.setLogLevel("ERROR")
  var myVertices = sc.makeRDD(Array(
    (1L, new node(id = 1, monster = new Monster("Solar", 363, 15, 44,
      new Weapon("GreatSword",5,35,18,3,6)))),
    (2L, new node(id = 2, monster = new Monster("Worg Rider", 13, 0, 18,
      new Weapon("battleaxe",1,6,2,1,8)))),
    (3L, new node(id = 3, monster = new Monster("Worg Rider", 13, 0, 18,
      new Weapon("battleaxe",1,6,2,1,8)))),
    (4L, new node(id = 4, monster = new Monster("Worg Rider", 13, 0, 18,
      new Weapon("battleaxe",1,6,2,1,8)))),
    (5L, new node(id = 5, monster = new Monster("Worg Rider", 13, 0, 18,
      new Weapon("battleaxe",1,6,2,1,8)))),
    (6L, new node(id = 6, monster = new Monster("Worg Rider", 13, 0, 18,
      new Weapon("battleaxe",1,6,2,1,8)))),
    (7L, new node(id = 7, monster = new Monster("Worg Rider", 13, 0, 18,
      new Weapon("battleaxe",1,6,2,1,8)))),
    (8L, new node(id = 8, monster = new Monster("Worg Rider", 13, 0, 18,
      new Weapon("battleaxe",1,6,2,1,8)))),
    (9L, new node(id = 9, monster = new Monster("Worg Rider", 13, 0, 18,
      new Weapon("battleaxe",1,6,2,1,8)))),
    (10L, new node(id = 10, monster = new Monster("Worg Rider", 13, 0, 18,
      new Weapon("battleaxe",1,6,2,1,8)))),
    (11L, new node(id = 11, monster = new Monster("Warlord", 141, 0, 27,
      new Weapon("vicious flail", 3,20,10,1,8)))),
    (12L, new node(id = 12, monster = new Monster("Barbares Orc", 142, 0, 17,
      new Weapon("orc double axe",3,19,10,1,8)))),
    (13L, new node(id = 13, monster = new Monster("Barbares Orc", 142, 0, 17,
      new Weapon("orc double axe",3,19,10,1,8)))),
    (14L, new node(id = 14, monster = new Monster("Barbares Orc", 142, 0, 17,
      new Weapon("orc double axe",3,19,10,1,8)))),
    (15L, new node(id = 15, monster = new Monster("Barbares Orc", 142, 0, 17,
      new Weapon("orc double axe",3,19,10,1,8))))))

  var myEdges = sc.makeRDD(Array(
    Edge(1L, 2L, "1"), Edge(1L, 3L, "2"),
    Edge(1L, 4L, "3"), Edge(1L, 5L, "4"),
    Edge(1L, 6L, "5"), Edge(1L, 7L, "6"),
    Edge(1L, 8L, "7"), Edge(1L, 9L, "8"),
    Edge(1L, 10L, "9"), Edge(1L, 11L, "10"),
    Edge(1L, 12L, "11"), Edge(1L, 13L, "12"),
    Edge(1L, 14L, "13"), Edge(1L, 15L, "14")
  ))

  val myGraph = Graph(myVertices,myEdges)

  /*val attaqueOrder: VertexRDD[(Int, Int, Int)] = myGraph.aggregateMessages[(Int, Int, Int)]( // attaque, indice mouvement, Mouvement effectif
    triplet => {
        triplet.sendToDst(triplet.srcAttr.monster.Attack(triplet.dstAttr.monster,1),0,0)
        println(triplet.srcAttr.toString + " attaque " + triplet.dstAttr.toString)
      /*} else {
        triplet.sendToSrc(0, triplet.srcAttr.MovingToTarget(triplet.dstAttr.position)._1, triplet.srcAttr.MovingToTarget(triplet.dstAttr.position)._2)
        println(triplet.srcAttr.toString + " moving to " + triplet.dstAttr.toString)
      }*/
    }
  //(a, b) => (a._1 + b._1, a._2 + b._2, a._3 + b._3)
  )*/


}
