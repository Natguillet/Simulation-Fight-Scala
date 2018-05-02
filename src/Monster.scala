
import scala.util.Random;

class Monster(var name: String, var life: Int, var regen: Int, var armor: Int, var weapon: Weapon, var numberOfAttack : Int = 0) extends Serializable{


  def dead() : Boolean  =
  {
    this.life <=0
  }

  def isTouch( defender : Monster) : Boolean =
  {
    val r = new Random()

    val precision = this.weapon.firstAttackPrecision - ((this.numberOfAttack - 1) * 5)
    precision + r.nextInt(20) > defender.armor
  }
  def takeDamage(damage : Int): Unit =
  {
    this.life -= damage
  }


  def Attack(defender : Monster): Int ={
    var damage = 0
    val r = new Random()
    var a =0;
      if(isTouch(defender))
    {
      for (a <- 1 to this.weapon.numberDice)
        {
          damage += r.nextInt(this.weapon.maxValueDice)
        }
      damage += this.weapon.baseDamage
    }
    //this.numberOfAttack += 1;
    damage
  }
}

