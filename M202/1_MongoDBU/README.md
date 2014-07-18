Instructions for Getting Started
================================

  1. <a href="https://www.vagrantup.com/downloads">Install vagrant</a>.
  2. <a href="https://www.virtualbox.org/wiki/Downloads">Install virtualbox</a>.
  3. In this directory, type 'vagrant up' at the terminal or command prompt.
  4. If you run into trouble, see the <a href="https://docs.vagrantup.com/v2/">documentation</a>.

FAQ
===

Q: What are we doing when we install this stuff and type 'vagrant up'?

A: We are installing identical virtual machines that you, as the students, will
    use for your environment in m202. In this case, the virtual machines will
    run a linux distribution, Ubuntu 12.04.

Q: Why are we installing a virtual machine?

A: M202 is an advanced DBA course, with a lot of hands on problems. It can be
    difficult to prepare problems that work (and work well) on all machines.
    By having all students run the same virtual machine, we are more likely to
    have a consistent experience from student to student, and you, as the
    student, are less likely to be distrcted by the peculiarities of your
    operating system while you are learning to deal with advanced mongoDB
    issues.

Q: Why are we using linux?

A: It is free, supported by MongoDB, and popular to use in servers.

Q: Do I need to use this virtual machine?

A: No. However, if you don't, you're on your own if you see some quirks in the
    behavior of the course software and you're not sure where they're coming
    from.

Q: What if I don't want to use a GUI?

A: That's fine, you can either use your own OS or spin up a virtual machine on
    your own. We created this by creating a Vagrant image from
    hashicorp/precise64. We then deleted a few things we don't require, and
    added a few things that we require for this course:

  * <a href="http://www.mongodb.org/downloads">mongoDB</a>
  * <a href="https://github.com/rueckstiess/mtools">mtools</a>
  * <a href="https://university.mongodb.com/mongoproc">mongoProc</a>

Q: What is MongoProc?

A: MongoProc is an internal tool we created at MongoDB University to prepare
    your MongoDB database into a paritcular state and/or verify that you've
    done something with it (such as modifying a document). It allows us
    to run python scripts on your system, so we can also do things like
    check that you've installed something correctly, or that an expected
    file is present.
