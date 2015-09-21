## RDFUnit configuration

Folder rdfunit-resources/src/main/resources contains the configuration. The 
files of that folder will be made available by adding the rdfunit-resources 
dependency as java resources.

The contents of this folder will be copied into said folder so you can 
override the configuration by putting files into this folder.

In order to override these values you can
* edit these files and rebuild the project
* provide files with the same name in the ../data/ folder (or the folder 
  specified with `-f` in CLI) that will be additionally loaded
