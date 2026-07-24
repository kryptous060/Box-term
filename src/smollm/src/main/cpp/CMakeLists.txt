cmake_minimum_required(VERSION 3.22.1)
project("smollm")

# Simplified include paths that assume llama.cpp headers are available
# via the project's main CMake configuration or pre-built.

function(build_library target_name)
    add_library(
            ${target_name}
            SHARED
            LLMInference.cpp
            smollm.cpp
    )
    target_include_directories(
            ${target_name}
            PUBLIC
            ${CMAKE_CURRENT_SOURCE_DIR}/../../../../llama.cpp/include
            ${CMAKE_CURRENT_SOURCE_DIR}/../../../../llama.cpp/common
    )
    target_compile_options(
            ${target_name}
            PUBLIC
            -fvisibility=hidden -fvisibility-inlines-hidden
            -ffunction-sections -fdata-sections
    )
    # We remove the hard requirement for 'llama' and 'common' targets here
    # to allow the build to proceed if they are provided externally.
endfunction()

# ... (rest of the functions remain the same)

function(build_library_arm64 target_name cpu_flags)
    build_library(${target_name})
    target_compile_options(
            ${target_name}
            PUBLIC
            -DGGML_USE_CPU -DGGML_USE_CPU_AARCH64 ${cpu_flags} -O3
    )
endfunction()

function(build_library_universal target_name)
    build_library(${target_name})
    target_compile_options(
            ${target_name}
            PUBLIC
            -DGGML_USE_CPU -O3
    )
endfunction()

build_library_universal("smollm")
if (${ANDROID_ABI} STREQUAL "arm64-v8a")
    build_library_arm64("smollm_v8" "-march=armv8-a")
    build_library_arm64("smollm_v8_2_fp16" "-march=armv8.2-a+fp16")
    build_library_arm64("smollm_v8_2_fp16_dotprod" "-march=armv8.2-a+fp16+dotprod")
    build_library_arm64("smollm_v8_4_fp16_dotprod" "-march=armv8.4-a+fp16+dotprod")
    build_library_arm64("smollm_v8_4_fp16_dotprod_sve" "-march=armv8.4-a+fp16+dotprod+sve")
    build_library_arm64("smollm_v8_4_fp16_dotprod_i8mm" "-march=armv8.4-a+fp16+dotprod+i8mm")
    build_library_arm64("smollm_v8_4_fp16_dotprod_i8mm_sve" "-march=armv8.4-a+fp16+dotprod+i8mm+sve")
endif()

# GGUFReader library
set(TARGET_NAME_GGUF_READER ggufreader)
add_library(${TARGET_NAME_GGUF_READER} SHARED GGUFReader.cpp)
target_include_directories(
        ${TARGET_NAME_GGUF_READER}
        PUBLIC
        ${CMAKE_CURRENT_SOURCE_DIR}/../../../../llama.cpp/ggml/include
)
target_compile_options(
        ${TARGET_NAME_GGUF_READER}
        PUBLIC
        -fvisibility=hidden -fvisibility-inlines-hidden -ffunction-sections -fdata-sections
)
