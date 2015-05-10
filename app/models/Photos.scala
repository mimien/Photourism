package models

import java.sql.DriverManager

import play.api.libs.json.{Json, JsValue}

/**
 * @author emiliocornejo
 * @version 30/04/15
 *          @(#)Photos.scala
 */
case class Photo(name: String, id: Option[Int] = None)
object Photos {
  val db = DriverManager.getConnection("jdbc:postgresql://localhost/Photourism")

  val st = db.createStatement()

  def poligonos: JsValue= {
    val query = st.executeQuery("SELECT ST_astext(pto) as geom from ubicacion where id > 9;")
    var rowsQuery = Array[String]()
    while (query.next()) rowsQuery = rowsQuery :+ query.getString(1).drop(9).dropRight(2)
    Json.toJson(rowsQuery)
  }

  def insertarPoligono(poligono: String) {
    st.executeUpdate("INSERT into ubicacion(pto) values(ST_GeomFromText('POLYGON((" + poligono + "))',4326));")
  }
}
