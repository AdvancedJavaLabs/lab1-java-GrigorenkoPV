{
  pkgs ? import <nixpkgs> { },
}:
pkgs.mkShellNoCC {
  preferLocalBuild = true;
  allowSubstitutes = false;

  name = "par-prog-lab1";
  packages = with pkgs; [
    jdk8
    (python3.withPackages (
      p: with p; [
        matplotlib
        numpy
      ]
    ))
  ];
}
