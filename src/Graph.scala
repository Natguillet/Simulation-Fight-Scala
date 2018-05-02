
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.graphx.{Edge, EdgeContext, Graph, _}


class node(var id: Int, var monster: Monster )extends Serializable {
  override def toString: String = s"id : $id monster Life : ${monster.life} monster name : ${monster.name}"
}

object Graphh extends App{

  val conf = new SparkConf()
    .setAppName("Fight")
    .setMaster("local[*]")
  val sc = new SparkContext(conf)
  sc.setLogLevel("ERROR")
  var myVertices = sc.makeRDD(Array(
    (1L, new node(id = 1, monster = new Monster("Worg Rider", 13, 0, 18,
      new Weapon("battleaxe",1,6,2,1,8)))),
    (2L, new node(id = 2, monster = new Monster("Solar", 363, 15, 44,
      new Weapon("GreatSword",5,35,18,3,6)))),
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
    Edge(2L, 1L, "1"), Edge(2L, 3L, "2"),
    Edge(2L, 4L, "3"), Edge(2L, 5L, "4"),
    Edge(2L, 6L, "5"), Edge(2L, 7L, "6"),
    Edge(2L, 8L, "7"), Edge(2L, 9L, "8"),
    Edge(2L, 10L, "9"), Edge(2L, 11L, "10"),
    Edge(2L, 12L, "11"), Edge(2L, 13L, "12"),
    Edge(2L, 14L, "13"), Edge(2L, 15L, "14")
  ))

  val myGraph = Graph(myVertices,myEdges)

  def sendTAttackValue(ctx: EdgeContext[node, String, Int]): Unit = {
    println("Monstre distance Name: "+ctx.dstAttr.monster.name + " Id:" + ctx.dstAttr.id + "<----" + "Monstre source Name: "+ctx.srcAttr.monster.name + " Id:" + ctx.srcAttr.id)

    ctx.sendToDst(ctx.srcAttr.monster.Attack(ctx.dstAttr.monster, 1))
  }

  def selectBest(dist1: Int, dist2: Int): Int = {
    println("Distance 1:" + dist1)
    println("Distance 2:" + dist2)
    if(dist1 > dist2) dist1
    else dist2
  }

  def takeDamage(vid: VertexId, nodeMonster: node, damage: Int): node = {
    println("Take dammage monster name:" + nodeMonster.monster.name + " ID: " + nodeMonster.id + "Damage: " + damage)
    nodeMonster.monster.takeDamage(damage)
    return new node(nodeMonster.id, nodeMonster.monster)
  }

  def execute(g: Graph[node, String], maxIterations: Int, sc: SparkContext): Graph[node, String] = {
    var myGraph = g
    var counter = 0
    val fields = new TripletFields(true, true, false) //join strategy

    def loop1: Unit = {
      while (true) {

        println("ITERATION NUMERO : " + (counter + 1))
        counter += 1
        if (counter == maxIterations) return

        val messages = myGraph.aggregateMessages[Int](
          sendTAttackValue,
          selectBest,
          fields //use an optimized join strategy (we don't need the edge attribute)
        )

        if (messages.isEmpty()) return

        myGraph = myGraph.joinVertices(messages)(
          (vid, nodeMonster, damage) => takeDamage(vid, nodeMonster, damage))

        //Ignorez : Code de debug
        var printedGraph = myGraph.vertices.collect()
        printedGraph = printedGraph.sortBy(_._1)
        printedGraph.foreach(
          elem => println(elem._2)
        )
      }

    }

    loop1 //execute loop
    myGraph //return the result graph
  }

  val res = execute(myGraph, 3, sc)
}
