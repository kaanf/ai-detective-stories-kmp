#!/bin/sh

set -eu

echo "ci_post_clone: ensuring a compatible JDK is available"

if /usr/libexec/java_home -v 21 >/dev/null 2>&1; then
    echo "Found JDK 21 at $(/usr/libexec/java_home -v 21)"
    exit 0
fi

if /usr/libexec/java_home -v 17 >/dev/null 2>&1; then
    echo "Found JDK 17 at $(/usr/libexec/java_home -v 17)"
    exit 0
fi

case "$(uname -m)" in
    arm64)
        adoptium_arch="aarch64"
        ;;
    x86_64)
        adoptium_arch="x64"
        ;;
    *)
        echo "Unsupported macOS architecture: $(uname -m)" >&2
        exit 1
        ;;
esac

workspace_dir="${CI_WORKSPACE_PATH:-$PWD}"
tmp_dir="${workspace_dir}/.xcode-cloud"
jdk_extract_dir="${tmp_dir}/jdk-extract"
archive_path="${tmp_dir}/temurin-17.tar.gz"
jdk_root="${HOME}/Library/Java/JavaVirtualMachines"
jdk_bundle="${jdk_root}/temurin-17.jdk"

mkdir -p "${tmp_dir}" "${jdk_root}"
rm -rf "${jdk_extract_dir}" "${archive_path}"
mkdir -p "${jdk_extract_dir}"

echo "Downloading Temurin 17 for ${adoptium_arch}"
curl -fsSL \
    "https://api.adoptium.net/v3/binary/latest/17/ga/mac/${adoptium_arch}/jdk/hotspot/normal/eclipse" \
    -o "${archive_path}"

echo "Extracting JDK bundle"
tar -xzf "${archive_path}" -C "${jdk_extract_dir}"

extracted_bundle="$(find "${jdk_extract_dir}" -maxdepth 1 -mindepth 1 -type d | head -n 1)"
if [ -z "${extracted_bundle}" ]; then
    echo "Failed to locate the extracted JDK bundle." >&2
    exit 1
fi

rm -rf "${jdk_bundle}"
mv "${extracted_bundle}" "${jdk_bundle}"
rm -rf "${jdk_extract_dir}" "${archive_path}"

if /usr/libexec/java_home -v 17 >/dev/null 2>&1; then
    java_home_value="$(
        /usr/libexec/java_home -v 17
    )"
    export JAVA_HOME="${java_home_value}"
    export PATH="${JAVA_HOME}/bin:${PATH}"

    echo "Installed JDK 17 at ${JAVA_HOME}"
    java -version
    exit 0
fi

echo "JDK 17 was installed, but /usr/libexec/java_home could not resolve it." >&2
echo "Expected bundle location: ${jdk_bundle}" >&2
exit 1
