#!/bin/bash

BUILD_OPTION=0

function usage() {
 cat <<EOF
  Usage:
  $(basename "$0") [options]
  Build script for the project

  Options:
    -b <target>, --build <target>: Build image
                                Possible targets for build:
                                            - front-end
                                            - data-access
                                            - provisioning-app
                                            - all
    -h,--help: Display help information
EOF
}

function parse_arguments() {
    PARSED_OPTIONS=$(getopt -n "$0" -o h,b: -l help,build: -- "$@")
    OPTION_RET=$?
    eval set -- "${PARSED_OPTIONS}"
    if [[ ${OPTION_RET} -ne 0 ]]
    then
        usage
        exit 1
    fi
    while [[ $# -ge 1 ]]
    do
        case $1 in
            -b | --build )
                    shift;
                    BUILD_OPTION=1
                    BUILD_SELECTION="${BUILD_SELECTION} $1"
            ;;
            -h | --help ) usage;exit 0;;
            -- ) shift;break;;
            * ) "Unsupported parameter $1";exit 2;;
        esac
        shift
    done
}

function generateImages() {
    local images="data-access front-end provisioning-app"

    for image in $images; do
        if [[ ${BUILD_SELECTION} =~ ${image} || ${BUILD_SELECTION} =~ 'all' ]]; then
            docker build -t "${image}":1.0.0 -f "${image}"/Dockerfile . \
                --build-arg http_proxy=http://www-proxy.ericsson.se:8080/ \
                --build-arg https_proxy=http://www-proxy.ericsson.se:8080/
        fi
    done

}

function build() {
    generateImages
}


function main() {
    parse_arguments "$@"

    if [[ ${BUILD_OPTION} -eq 1 ]]; then
        build
    fi
}

main "$@"
