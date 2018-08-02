//
// Created by 刘丰恺 on 2018/8/1.
//

#ifndef LLVM_RUNNER_ASTNODE_H
#define LLVM_RUNNER_ASTNODE_H

#include <nlohmann/json.hpp>

using json=nlohmann::json;

class AstNode {
public:
    AstNode(const json &load_json);

    virtual ~AstNode() {}

    const json &get_json();

private:
    json load_json;
};

using AstNodePointer = std::shared_ptr<AstNode>;

#endif //LLVM_RUNNER_ASTNODE_H
