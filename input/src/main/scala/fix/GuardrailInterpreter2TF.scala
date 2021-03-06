/*
rule = GuardrailInterpreter2TF
*/
package fix

object GuardrailInterpreter2TF {
  type Target[A] = Option[A]
  type JavaLanguage = Unit
  abstract class ~>[F[_], G[_]] {
    def apply[A](term: F[A]): G[A]
  }

  sealed trait ScalaTerm[L, A]

  case class Bar(a: Boolean, b: String)
  case class VendorPrefixes[L](a: Long, b: Bar) extends ScalaTerm[L, List[String]]

  object JavaInterp extends (ScalaTerm[JavaLanguage, ?] ~> Target) {
    type F[A] = Target[A]
    type L = JavaLanguage

    def vendorPrefixes(a: Long, b: Bar): F[List[String]] = ???

    def apply[T](term: ScalaTerm[JavaLanguage, T]): Target[T] = term match {
      case VendorPrefixes(foo, Bar(_a, _b)) =>
        for {
          a <- Option(foo.toString())
          b <- Option(_b)
        } yield List(a, b)
    }
  }
}
