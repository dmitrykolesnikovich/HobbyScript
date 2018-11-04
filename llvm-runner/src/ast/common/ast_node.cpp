//
// Created by 刘丰恺 on 2018/8/1.
//

#include "ast_node.h"

ast_node::ast_node(const json &load_json) {
    this->load_json = load_json;
    this->tag = load_json["tag"];
}

const json &ast_node::get_json() {
    return load_json;
}