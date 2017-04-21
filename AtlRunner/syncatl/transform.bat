
@java -ea -jar ../BiXM.jar --f --trans file:Make2Ant.asm --src IN=Source.Make Make=Make.ecore --tgt OUT=target.Ant Ant=Ant.ecore
@copy source.Make source-modified.Make
@copy target.Ant target-modified.Ant
