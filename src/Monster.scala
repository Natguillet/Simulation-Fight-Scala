
import scala.util.Random;

class Monster(var name: String, var life: Int, var regen: Int, var armor: Int, var weapon: Weapon) {


  def dead() : Boolean  =
  {
    if (this.life <=0)
      {
        true
      }
    else
      {
        false
      }
  }

  def isTouch( defender : Monster, attackNumber : Int) : Boolean =
  {
    val r = new Random()

    val precision = this.weapon.firstAttackPrecision - ((attackNumber - 1) * 5)
      if (precision + r.nextInt(20) > defender.armor)
        {
          true
        }
      else
        {
          false
        }
  }
  def takeDamage(damage : Int): Unit =
  {
    this.life -= damage
  }

  def Attack(defender : Monster, attackNumber : Int): Int ={
    var damage = 0
    val r = new Random()
    var a =0;
      if(isTouch(defender, attackNumber))
    {
      for (a <- 1 to this.weapon.numberDice)
        {
          damage += r.nextInt(this.weapon.maxValueDice)
        }
      damage += this.weapon.baseDamage
    }
    damage
  }
}

