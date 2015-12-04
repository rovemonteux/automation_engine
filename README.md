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

Usage
=====

java -jar target/Monteux_Automation_Engine-0.0.1-SNAPSHOT-jar-with-dependencies.jar -hid Console -vocabulary vocabularies/test_vocabulary.xml -script scripts/test_script_custom_vocabulary.txt

**-hid**
Selects the desired **HID**, i.e. 'Console' for the interactive shell, 'Script' for the headless script interpreter.

**-vocabulary**
Custom vocabulary binding **Tasks** to commands, use 'builtin' or don't send the flag for no custom vocabulary.

**-script**
Custom script containing commands to be run at the engine's initialization.

**Project Wiki**
[https://github.com/rovemonteux/automation_engine/wiki](https://github.com/rovemonteux/automation_engine/wiki)
