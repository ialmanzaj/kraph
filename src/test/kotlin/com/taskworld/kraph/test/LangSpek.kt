package com.taskworld.kraph.test

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasElement
import com.natpryce.hamkrest.hasSize
import com.natpryce.hamkrest.isA
import com.taskworld.kraph.OperationType
import com.taskworld.kraph.QL
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

/**
 * Created by VerachadW on 9/20/2016 AD.
 */
@RunWith(JUnitPlatform::class)
class LangSpek : Spek({
    describe("Kraph Query DSL Builder") {
        given("smaple query") {
            val query = QL {
                query("getAllNotes") {
                    fieldObject("notes") {
                        field("id")
                        field("content")
                        fieldObject("author") {
                            field("name")
                            field("email")
                        }
                        field("avatarUrl", params = mapOf("size" to 100))
                    }
                }
            }
            it("should have query operation type") {
                assertThat(query.document.operation.type, isA(equalTo(OperationType.QUERY)))
            }
            it("should have only one field inside query") {
                assertThat(query.document.operation.fields, hasSize(equalTo(1)))
            }
            it("should have field named notes inside query") {
                assertThat(query.document.operation.fields[0].name, equalTo("notes"))
            }
            it("should have four fields inside note object") {
                assertThat(query.document.operation.fields[0].selectionSet!!.fields, hasSize(equalTo(4)))
            }
            it("should have field named id inside notes object") {
                assertThat(query.document.operation.fields[0].selectionSet!!.fields[0].name, equalTo("id"))
            }
            it("should have field named content inside notes object") {
                assertThat(query.document.operation.fields[0].selectionSet!!.fields[1].name, equalTo("content"))
            }
            it("should have field named author inside notes object") {
                assertThat(query.document.operation.fields[0].selectionSet!!.fields[2].name, equalTo("author"))
            }
            it("should have field named avatarUrl inside notes object") {
                assertThat(query.document.operation.fields[0].selectionSet!!.fields[3].name, equalTo("avatarUrl"))
            }
            it("should have size argument with value 100 for avatarUrl field") {
                assertThat(query.document.operation.fields[0].selectionSet!!.fields[3].arguments!!.args.keys, hasElement("size"))
                assertThat(query.document.operation.fields[0].selectionSet!!.fields[3].arguments!!.args["size"] as Int, equalTo(100))
            }
            it("should have two fields inside author object") {
                assertThat(query.document.operation.fields[0].selectionSet!!.fields[2].selectionSet!!.fields, hasSize(equalTo(2)))
            }
            it("should have field named name inside author object") {
                assertThat(query.document.operation.fields[0].selectionSet!!.fields[2].selectionSet!!.fields[0].name, equalTo("name"))
            }
            it("should have field named email inside author object") {
                assertThat(query.document.operation.fields[0].selectionSet!!.fields[2].selectionSet!!.fields[1].name, equalTo("email"))
            }
        }
    }
})