package models

import java.sql.DriverManager

import play.api.libs.json.{JsValue, Json}

/**
 * @author emiliocornejo
 * @version 30/04/15
 *          @(#)Photos.scala
 */
case class Photo(path: String, place: String /*, priv: Boolean, point: String*/)

object Photos {
  val db = DriverManager.getConnection("jdbc:postgresql://localhost/Photourism")
  val st = db.createStatement()
  private var _point: String = _

  def all: JsValue = {
    val query = st.executeQuery("SELECT ST_astext(point) as geom from  \"Photo\";")
    var rowsQuery = Array[String]()
    while (query.next()) rowsQuery = rowsQuery :+ query.getString(1).drop(6).dropRight(1)
    Json.toJson(rowsQuery)
  }

  def add(photo: Photo, email: String) = {
    println(_point)
    st.executeUpdate("INSERT into \"Photo\"(path, place, point) " +
      "VALUES('" + photo.path + "', '" + photo.place + "', ST_GeomFromText('POINT(" + _point + ")',4326));")

    val query = st.executeQuery("SELECT id from \"User\" where email = '" + email + "';")
    if (!query.next()) print("error")
    val idUser = query.getInt(1)


    val query2 = st.executeQuery("SELECT id from \"Photo\" where path = '" + photo.path + "';")
    if (!query2.next()) print("error")
    val idPhoto = query2.getInt(1)

    st.executeUpdate("INSERT into \"UserPhotos\"(\"userId\", \"photoId\") " +
      "VALUES('" + idUser + "', '" + idPhoto + "');")
  }

  def point_=(latLng: String) {
    println(latLng)
    _point = latLng
  }
}
