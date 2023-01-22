import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProdutoFornecedor } from '../produto-fornecedor.model';

@Component({
  selector: 'jhi-produto-fornecedor-detail',
  templateUrl: './produto-fornecedor-detail.component.html',
})
export class ProdutoFornecedorDetailComponent implements OnInit {
  produtoFornecedor: IProdutoFornecedor | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ produtoFornecedor }) => {
      this.produtoFornecedor = produtoFornecedor;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
