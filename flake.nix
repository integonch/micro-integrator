{
    description            = "micro-integrator-dev-shell";
    inputs.flake-utils.url = "github:numtide/flake-utils";
    inputs.nixpkgs.url     = "github:NixOS/nixpkgs";

    outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachSystem
    [
        "x86_64-darwin" 
        "aarch64-darwin" 
        "x86_64-linux" 
    ]
    (system:
        let pkgs = nixpkgs.legacyPackages.${system};
    in
    {
        devShell = with pkgs; mkShell
        {
            buildInputs =
            [
                (maven.override { jdk = openjdk8; })
                openjdk8
            ];
        };
    });
}