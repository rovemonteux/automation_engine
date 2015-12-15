**Monteux Automation Engine**
====================

**Monteux Automation Engine** is a GPLed framework for Java SE 6 or newer that implements a Lego(tm)-like modular, plug and play Automation Framework with a built-in shell and script interpreter.

The automation is based on **tasks lists** and **object stacks**, interacting with the environment via **HIDs**. 

**Tasks** are classes described in custom **vocabularies**, with three entry points: **run**, **stack** and **print**.

**run** runs a full cycle of the task, typically running, pushing the result into the stack, and pritning the result to the **HID** stream.

**stack** runs the task and pushes the result to the **stack**.

**print** runs the task and prints the result to the **HID** output stream.

**Object Stack** is an in-memory database of objects acting as a **stack**, with add, search, list, retrieve and clear functionalities. Objects in the **stack** are immediately available for all running **tasks**.

**HID**s are devices for interacting with the automation engine, such as **Console** for a Readline-enabled, interactive shell, and **Script** for a headless engine.

Examples are included in the distribution, in the folders,

**test/**
scripts for starting the engine.

**scripts/**
scripts to be included and trun at the engine's startup.

**vocabularies/**
vocabularies binding tasks to commands to be executed via scripts or interactively in the HIDs.

Build
=====

For building the **Monteux Automation Engine**, you will need Maven. In the project folder, type

```
mvn package
```
  
Usage
=====

```
java -jar target/Monteux_Automation_Engine-0.0.1-SNAPSHOT-jar-with-dependencies.jar -hid Console -vocabulary vocabularies/test_vocabulary.xml -script scripts/test_script_custom_vocabulary.txt
```

**-hid**
Selects the desired **HID**, i.e. 'Console' for the interactive shell, 'Script' for the headless script interpreter.

**-vocabulary**
Custom vocabulary binding **Tasks** to commands, use 'builtin' or don't send the flag for no custom vocabulary.

**-script**
Custom script containing commands to be run at the engine's initialization.

Extensions
==========

To extend the **Monteux Automation Engine**, simply write your own **Task**, extending net.rovemonteux.automation.engine.tasks.TaskFactory, and bind it as a **Task** using a custom vocabulary, for example,

```
package my.package.name;

public class MyExtension extends TaskFactory {
```

bind it as a **Task** with a custom **vocabulary** term in the **vocabulary** file,

```
<term>
<language>en</language>
<value>my extension</value>
<task mode="run">MyExtension</task>
<threaded>false</threaded>
<description>Runs my custom extension</description>
<package>my.package.name</package>
</term>
```

Then run the new extension in the **Monteux Automation Engine** via the command "my extension" - the "run()" method of the class "MyExtension" will then be given access to the **Object Stack** and run in non-threaded mode.

To run in threaded mode, in its own thread, change the threaded configuration tag from

```
<threaded>false</threaded>
```

to

```
<threaded>true</threaded>
```

To run the "stack()" or "print()" methods, simply change the **mode** attribute in **task** accordingly.

You can also chain tasks together to a single command, as in for example,

```
<term>
<language>en</language>
<value>my extension</value>
<task mode="print">ShowStack</task>
<task mode="stack">MyExtension</task>
<task mode="print">MyExtension</task>
<task mode="run">MyExtension</task>
<task mode="print">ShowStack</task>
<threaded>false</threaded>
<description>Runs my custom extension</description>
<package>my.package.name</package>
</term>
```

An example **extension** is the Empty Stack one, which clears the **object stack** via the "empty stack" command in the **Monteux Automation Engine**,

**Extension source code:** https://github.com/rovemonteux/automation_engine/blob/master/src/main/java/net/rovemonteux/automation/engine/tasks/EmptyStack.java

**Vocabulary:** https://github.com/rovemonteux/automation_engine/blob/master/vocabularies/test_vocabulary.xml

Author
======

* [Rove Monteux] - <https://rmonteux.wordpress.com/>, <https://www.linkedin.com/in/rovemonteux>

License
=======

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

Project Wiki
============

* [https://github.com/rovemonteux/automation_engine/wiki](https://github.com/rovemonteux/automation_engine/wiki)
