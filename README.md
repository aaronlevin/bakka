# Bakka

Bash is fun, but so is Akka and Scala. Wouldn't it be fun, and kinda [ridiculous](http://en.wikipedia.org/wiki/Baka_(word\)), to implement some of the great command-line unix tools (read: *pipe*) using Akka?

    Cat("ye_olde_list_of_banach_algebras.txt") {
      ( Grep("^C\\*")
        |> Head(3)
        |> Append(" are the coolest algebras!")
        |> Func( (str: String) => DB.addAlgebra(str) )
        |> Echo( (str: String) => "Just added " + str + " to the algebra db!" )
      )
    }

# Caveat

This project is not yet ready for anything and is lacking in high-level abstraction. It is mostly busy work in my dwindling personal time.
